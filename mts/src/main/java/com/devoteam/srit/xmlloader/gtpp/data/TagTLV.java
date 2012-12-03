/* 
 * Copyright 2012 Devoteam http://www.devoteam.com
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * 
 * This file is part of Multi-Protocol Test Suite (MTS).
 * 
 * Multi-Protocol Test Suite (MTS) is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the
 * License.
 * 
 * Multi-Protocol Test Suite (MTS) is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Multi-Protocol Test Suite (MTS).
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.devoteam.srit.xmlloader.gtpp.data;

import com.devoteam.srit.xmlloader.core.utils.dictionaryElement.Attribute;
import com.devoteam.srit.xmlloader.core.utils.dictionaryElement.TLV;
import com.devoteam.srit.xmlloader.gtpp.GtppDictionary;

import gp.utils.arrays.*;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

import org.dom4j.Element;

/**
 *
 * @author Benjamin Bouvier
 */
public class TagTLV extends Tag
{
    
    public TagTLV()
    {}

    @Override
    public Array getArray() throws Exception
    {
        SupArray array = new SupArray();
        if(getValueQuality())
        {
            array.addFirst(new Integer08Array(getTag()));
            if(!isFixedLength())
                array.addLast(new Integer16Array(getLength()));

            if(getFormat().equals("int"))
            {
                if(getLength() == 1)
                    array.addLast(new Integer08Array((Integer)getValue()));
                else if(getLength() == 2)
                    array.addLast(new Integer16Array((Integer)getValue()));
                else if(getLength() == 4)
                    array.addLast(new Integer32Array((Integer)getValue()));
                else if(getLength() == 8)
                    array.addLast(new Integer64Array((Integer)getValue()));                
            }
            else if(getFormat().equals("list"))
            {
                if(((LinkedList)getValue()).size() > 0)//search in attribute list
                {
                    for(int i = 0; i < ((LinkedList)getValue()).size(); i++)
                    {
                        Array ar = ((GtppAttribute)((LinkedList)getValue()).get(i)).getArray();
                        if(ar != null)
                            array.addLast(ar);
                    }
                }
            }
            else//same for ip format
                array.addLast(new DefaultArray((byte[])getValue()));
        }
        return array;
    }
    
    @Override
    public int parseArray(Array array, int index, GtppDictionary dictionary) throws Exception
    {
        if(!isFixedLength()) {
            setLength(new Integer16Array(array.subArray(index, 2)).getValue());
            index += 2;
        }
        //then get value or length
        Array value = array.subArray(index, getLength());
        index += getLength();
        if(getFormat().equals("int"))
        {
            if(getLength() == 1)
                setValue(new Integer08Array(value).getValue());
            else if (getLength() == 2)
                setValue(new Integer16Array(value).getValue());
            else if (getLength() == 4)
                setValue(new Integer32Array(value).getValue());
            else if (getLength() == 8)
                setValue(new Integer64Array(value).getValue());
        }
        else if(getFormat().equals("list"))
        {
            parseLinkedList(value, this, 0);
        }
        else
        {
            setValue(value.getBytes());
        }
        return index;
    }
    
    private int parseLinkedList(Array valueToDecode, Attribute att, int index) throws Exception
    {
        LinkedList<Object> list = (LinkedList<Object>)att.getValue();
        int lastSize = 0;
        while(index < valueToDecode.length)
        {
            for(int i = lastSize; i < list.size(); i++)
            {
                if(index < valueToDecode.length)
                {
                    GtppAttribute att2 = (GtppAttribute)list.get(i);
                    if(att2.getFormat().equals("list"))
                    {
                        index += parseLinkedList(valueToDecode, att2, index);//recursive call
                    }
                    else if(att2.getFormat().equals("int"))
                    {
                        int lg = att2.getLength();
                        if(lg == 1)
                            att2.setValue(new Integer08Array(valueToDecode.subArray(index, lg)).getValue());
                        else if(lg == 2)
                            att2.setValue(new Integer16Array(valueToDecode.subArray(index, lg)).getValue());
                        index += lg;
                    }
                    else if(att2.getValueQuality() || att2.getLength() == -1)//so based on the latest attribute
                    {
                        if(att2.getValueQuality())//duplicate att because already set
                        {
                            GtppAttribute duplicateAtt = att2.clone();
                            att2 = duplicateAtt;
                            list.add(att2);
                        }

                        att2.setLength((Integer)((GtppAttribute)list.get(i-1)).getValue());
                        att2.setValue(new DefaultArray(valueToDecode.getBytes(), index, att2.getLength()).getBytes());
                        index += att2.getLength();
                        //pb here to analyze
                    }
                }
                else
                {
                    System.out.println("index > valueTodecode.length");
                    break;
                }
            }
            lastSize = list.size();
            if(index < valueToDecode.length)//if the list is end, but we don't have decode all data=> duplicate last 2 attributes for data records and continue to decode
            {
                if(att.getName().equals("dataRecords"))//for data records clone the last 2 attributes
                {
                    //duplicate last 2 Attributes
                    list.add(((GtppAttribute)list.get(list.size() - 2)).clone());//size - 2 to clone length
                    list.add(((GtppAttribute)list.get(list.size() - 2)).clone());//anothert time size - 2 because just above, one other att added, so clone record
                    ((GtppAttribute)list.get(list.size() - 1)).setLength(-1);//reset lenght of last attribute dataRecords
                }
                else//else clone just the last attribute when attribute is a list of a lot of the same attribute
                    list.add(((GtppAttribute)list.get(list.size() - 1)).clone());
            }
        }
        return index;
    }

    @Override
     public Tag clone()
    {
        Tag clone = new TagTLV();
        clone.setAtt(new GtppAttribute());

        clone.setLength(getLength());
        clone.setName(getName());
        clone.setTag(getTag());
        clone.setFormat(getFormat());
        clone.setSizeMin(getSizeMin());
        clone.setSizeMax(getSizeMax());
        clone.setMandatory(isMandatory());
        clone.setFixedLength(isFixedLength());

        if((getValue() != null) && (getValue() instanceof LinkedList))
        {
            LinkedList<GtppAttribute> list = (LinkedList)getValue();
            LinkedList cloneList = new LinkedList();
            try {
                //                for(int i = 0; i < list.size(); i++)
                //                    cloneList.add(list.get(i).clone());
                cloneLinkedList(list, cloneList);
                clone.setValue(cloneList);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return clone;
    }
  
    private void cloneLinkedList(LinkedList list, LinkedList newList) throws CloneNotSupportedException
    {
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i) instanceof LinkedList)
            {
                LinkedList<GtppAttribute> cloneList = new LinkedList();
                cloneLinkedList(((LinkedList)list.get(i)), cloneList);
            }
            else
                newList.add(((GtppAttribute)list.get(i)).clone());
        }
    }

}
