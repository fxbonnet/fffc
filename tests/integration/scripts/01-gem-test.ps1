Write-Host "Installing Chocolatey..."
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))

$GEM_PATH="octo-fffc-latest.gem"

$GEM_FOLDER="c:/app/app-gem"
$DATA_FOLDER="c:/app/app-data/sets"

Write-Host "Installing Ruby..."
choco install -y ruby

Write-Host "Tracing dir: $GEM_FOLDER"
cd $GEM_FOLDER 
dir 

Write-Host  "Unisntalling gems"
gem uninstall octo-fffc --force -x

Write-Host  "Installing latest gem: $GEM_PATH"
gem install --local "$GEM_PATH" --no-ri --no-rdoc --force

if ($LastExitCode -ne 0) {
    Write-Host "Cannot install gem"
    exit 1
}

Write-Host  "Installing latest gem: $GEM_PATH"
$dirs = Get-ChildItem -Directory $DATA_FOLDER

foreach($dir in $dirs) {
    Write-Host "Testing dataset: $dir, $($dir.FullName)"

    octo-fffc `
        -i "$($dir.FullName)/data.txt" `
        -d "$($dir.FullName)/metadata.csv" `
        -o "$($dir.FullName)/.output/output.csv"

    if ($LastExitCode -ne 0) {
        Write-Host "[ERROR] Failed octo-fffc on folder: $($dir.FullName)"
        exit 1
    } else {
        Write-Host "[OK] Success octo-fffc on folder: $($dir.FullName)"
    }
}

exit 0