yarn build
rm -rf ../client/app/src/main/assets/*
cp -r dist/* ../client/app/src/main/assets
