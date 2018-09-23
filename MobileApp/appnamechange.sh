#!/bin/bash
cd app/src/main/res/values
sed -i 's/<string name="app_name">[a-zA-Z0-9  ]*['"'"']*[a-zA-Z0-9  ]*/<string name="app_name">'""$1""'/g' strings.xml
