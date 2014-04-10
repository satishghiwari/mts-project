package asn1;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;

@ASN1PreparedElement
@ASN1Choice(name = "Data")
public class Data implements IASN1PreparedElement {

    @ASN1Element(name = "plain", isOptional = false, hasTag = true, tag = 0, hasDefaultValue = false)
    private TestPRN plain = null;
    @ASN1Element(name = "unicode", isOptional = false, hasTag = true, tag = 1, hasDefaultValue = false)
    private TestOCT unicode = null;
    @ASN1Element(name = "binary", isOptional = false, hasTag = true, tag = 2, hasDefaultValue = false)
    public TestOCT binary = null;
    @ASN1String(name = "",
    stringType = UniversalTag.PrintableString, isUCS = false)
    @ASN1Element(name = "simpleType", isOptional = false, hasTag = true, tag = 3, hasDefaultValue = false)
    private String simpleType = null;
    @ASN1OctetString(name = "")
    @ASN1Element(name = "simpleOctType", isOptional = false, hasTag = true, tag = 4, hasDefaultValue = false)
    public byte[] simpleOctType = null;
    @ASN1Boolean(name = "")
    @ASN1Element(name = "booleanType", isOptional = false, hasTag = true, tag = 5, hasDefaultValue = false)
    private Boolean booleanType = null;
    @ASN1Integer(name = "")
    @ASN1Element(name = "intType", isOptional = false, hasTag = true, tag = 6, hasDefaultValue = false)
    private Long intType = null;
    @ASN1Integer(name = "")
    @ASN1ValueRangeConstraint(min = 0L,
    max = 255L)
    @ASN1Element(name = "intBndType", isOptional = false, hasTag = true, tag = 7, hasDefaultValue = false)
    private Integer intBndType = null;

    public TestPRN getPlain() {
        return this.plain;
    }

    public boolean isPlainSelected() {
        return this.plain != null;
    }

    private void setPlain(TestPRN value) {
        this.plain = value;
    }

    public void selectPlain(TestPRN value) {
        this.plain = value;

        setUnicode(null);

        setBinary(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public TestOCT getUnicode() {
        return this.unicode;
    }

    public boolean isUnicodeSelected() {
        return this.unicode != null;
    }

    private void setUnicode(TestOCT value) {
        this.unicode = value;
    }

    public void selectUnicode(TestOCT value) {
        this.unicode = value;

        setPlain(null);

        setBinary(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public TestOCT getBinary() {
        return this.binary;
    }

    public boolean isBinarySelected() {
        return this.binary != null;
    }

    private void setBinary(TestOCT value) {
        this.binary = value;
    }

    public void selectBinary(TestOCT value) {
        this.binary = value;

        setPlain(null);

        setUnicode(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public String getSimpleType() {
        return this.simpleType;
    }

    public boolean isSimpleTypeSelected() {
        return this.simpleType != null;
    }

    private void setSimpleType(String value) {
        this.simpleType = value;
    }

    public void selectSimpleType(String value) {
        this.simpleType = value;

        setPlain(null);

        setUnicode(null);

        setBinary(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public byte[] getSimpleOctType() {
        return this.simpleOctType;
    }

    public boolean isSimpleOctTypeSelected() {
        return this.simpleOctType != null;
    }

    private void setSimpleOctType(byte[] value) {
        this.simpleOctType = value;
    }

    public void selectSimpleOctType(byte[] value) {
        this.simpleOctType = value;

        setPlain(null);

        setUnicode(null);

        setBinary(null);

        setSimpleType(null);

        setBooleanType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public Boolean getBooleanType() {
        return this.booleanType;
    }

    public boolean isBooleanTypeSelected() {
        return this.booleanType != null;
    }

    private void setBooleanType(Boolean value) {
        this.booleanType = value;
    }

    public void selectBooleanType(Boolean value) {
        this.booleanType = value;

        setPlain(null);

        setUnicode(null);

        setBinary(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setIntType(null);

        setIntBndType(null);

    }

    public Long getIntType() {
        return this.intType;
    }

    public boolean isIntTypeSelected() {
        return this.intType != null;
    }

    private void setIntType(Long value) {
        this.intType = value;
    }

    public void selectIntType(Long value) {
        this.intType = value;

        setPlain(null);

        setUnicode(null);

        setBinary(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntBndType(null);

    }

    public Integer getIntBndType() {
        return this.intBndType;
    }

    public boolean isIntBndTypeSelected() {
        return this.intBndType != null;
    }

    private void setIntBndType(Integer value) {
        this.intBndType = value;
    }

    public void selectIntBndType(Integer value) {
        this.intBndType = value;

        setPlain(null);

        setUnicode(null);

        setBinary(null);

        setSimpleType(null);

        setSimpleOctType(null);

        setBooleanType(null);

        setIntType(null);

    }

    public void initWithDefaults() {
    }
    private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Data.class);

    public IASN1PreparedElementData getPreparedData() {
        return preparedData;
    }
}
