
#!/bin/bash
set -x

PATH=/usr/local/rbenv/bin:/usr/local/rbenv/shims:$PATH

GEM_PATH="octo-fffc-latest.gem"
GEM_FOLDER="/app-gem"
DATA_FOLDER="/app-data/sets"

echo 'Testing gem install'

echo 'Tracing pwd()'
pwd

echo "Tracing dir: $GEM_FOLDER"
cd "$GEM_FOLDER" && ls -la 

echo 'Tracing env'
/usr/local/rbenv/shims/ruby --version

echo "Unisntalling gems"
gem uninstall octo-fffc --force -x

echo "Installing latest gem: $GEM_PATH"
gem install --local "$GEM_PATH" --no-ri --no-rdoc --force
[ $? -ne 0 ] && echo "Cannot install gem" && exit 1

echo 'Executing regression tests on folder: $DATA_FOLDER'
ls -la DATA_FOLDER

for dir in $(find "$DATA_FOLDER" -mindepth 1 -maxdepth 1 -type d); do
    echo "Testing dataset: $dir"

    octo-fffc \
        -i "$dir/data.txt" \
        -d "$dir/metadata.csv" \
        -o "$dir/.output/output.csv"

    [ $? -ne 0 ] && echo "[ERROR] - Failed octo-fffc on folder: $dir" && exit 1
    [ $? -eq 0 ] && echo "[OK] - Success octo-fffc on folder: $dir"
done

exit 0