echo '#####################################'
echo '##      ui module build start      ##'
echo '#####################################'
docker build -f ./ui/Dockerfile_ui --tag ui_module:4.0.200.0 .
echo '#####################################'
echo '##      ui module build e n d      ##'
echo '#####################################'
echo ''
echo ''

echo '#####################################'
echo '##   contract module build start   ##'
echo '#####################################'
docker build -f ./contract/Dockerfile_contract --tag contract_module:4.0.200.0 .
echo '#####################################'
echo '##   contract module build e n d   ##'
echo '#####################################'
echo ''
echo ''

echo '#####################################'
echo '##    license module build start   ##'
echo '#####################################'
docker build -f ./license/Dockerfile_license --tag license_module:4.0.200.0 .
echo '#####################################'
echo '##    license module build e n d   ##'
echo '#####################################'
echo ''
echo ''

echo '#####################################'
echo '##      log module build start     ##'
echo '#####################################'
docker build -f ./log/Dockerfile_log --tag log_module:4.0.200.0 .
echo '#####################################'
echo '##      log module build e n d     ##'
echo '#####################################'
echo ''
echo ''

echo '#####################################'
echo '##    monitor module build start   ##'
echo '#####################################'
docker build -f ./monitor/Dockerfile_monitor --tag monitor_module:4.0.200.0 .
echo '#####################################'
echo '##    monitor module build e n d   ##'
echo '#####################################'
echo ''
echo ''

echo '#####################################'
echo '##     eureka module build start   ##'
echo '#####################################'
docker build -f ./eureka/Dockerfile_eureka --tag eureka_module:4.0.200.0 .
echo '#####################################'
echo '##     eureka module build e n d   ##'
echo '#####################################'
echo ''
echo ''

