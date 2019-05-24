#!/bin/bash
pwd
cd ../../MobileApp/app/src/main/res/drawable/
echo $1
sed -i 's/android:tint="#[A-Za-z0-9]*"/android:tint="'""$1""'"/g' *.xml
cd ../../../../../../WebApp/Conference_Website/