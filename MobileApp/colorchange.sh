#!/bin/bash
pwd
cd ../../MobileApp/app/src/main/res/values
echo $1
echo $2
echo $3
sed -i 's/<color name="colorPrimary">#[A-Za-z0-9]*/<color name="colorPrimary">'""$1""'/g' colors.xml
sed -i 's/<color name="colorPrimaryDark">#[A-Za-z0-9]*/<color name="colorPrimaryDark">'""$2""'/g' colors.xml
sed -i 's/<color name="colorAccent">#[A-Za-z0-9]*/<color name="colorAccent">'""$3""'/g' colors.xml
cd ../../../../../../WebApp/Conference_Website/