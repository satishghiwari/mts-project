echo off
echo Process the ASN files in the directory ../conf/sigtran/%1 : please confirm ?
pause

del /F /Q ..\java\com\devoteam\srit\xmlloader\sigtran\ap\generated\%1
mkdir ..\java\com\devoteam\srit\xmlloader\sigtran\ap\generated\%1

FOR %%i IN (../conf/sigtran/%1/*.asn) do call binaryNotes/bin/bncompiler.cmd -m java -o ../java/com/devoteam/srit/xmlloader/sigtran/ap/generated/%1 -ns com.devoteam.srit.xmlloader.sigtran.ap.generated.%1 -mp binaryNotes/bin/modules -f ../conf/sigtran/%1/%%i

javac -cp output/;../../lib/java/binarynotes.jar ./output/ldapv3/*.java



