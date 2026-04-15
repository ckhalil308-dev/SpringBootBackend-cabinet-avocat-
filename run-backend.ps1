param(
    [switch]$SkipTests
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSCommandPath
Set-Location $projectRoot

$javaCmd = Get-Command "java" -ErrorAction SilentlyContinue
if (-not $javaCmd) {
    Write-Error "Java not found. Install JDK 21+ and ensure 'java' is on PATH."
    exit 1
}

$buildArgs = @("clean", "package")
if ($SkipTests) {
    $buildArgs += "-DskipTests"
}

Write-Host "Building backend with Maven wrapper..." -ForegroundColor Cyan
& ".\mvnw.cmd" @buildArgs
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$jar = Get-ChildItem -Path "$projectRoot\target" -Filter "*.jar" -Recurse |
    Where-Object { $_.Name -notlike "*sources*" -and $_.Name -notlike "*javadoc*" } |
    Select-Object -First 1

if (-not $jar) {
    Write-Error "No runnable JAR found in target/."
    exit 1
}

Write-Host "Starting backend from $($jar.Name)..." -ForegroundColor Green
& "java" "-jar" $jar.FullName

