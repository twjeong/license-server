echo build discovery
cd discovery
call gradlew.bat clean build -x test

cd ..

echo build gateway
cd gateway
call gradlew.bat clean build -x test

cd ..

echo build license
cd license
call gradlew.bat clean build -x test

cd ..