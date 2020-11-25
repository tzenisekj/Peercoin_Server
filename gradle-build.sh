#!/bin/bash

mkdir web/lib/
mkdir manager/lib/

cd core/

gradle build

cp build/libs/* ../web/lib/
cp build/libs/*.jar ../manager/lib/

# cd ../manager
# gradle build

# cd ../web
# gradle build


