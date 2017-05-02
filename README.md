# meta-raspberrypi

Yocto Project Fork - BSP Layer Raspbery Pi, OP-TEE support.

fork from git://git.yoctoproject.org/meta-raspberrypi morty branch.

support OP-TEE (https://github.com/OP-TEE/)

open embedded meta layer for raspberry pi 3 64bit.


refer to

http://www.debugme.co.kr/2017/05/yocto-open-embedded-raspberrry-pi-3-op.html


to support OP-TEE

modify ARM Trusted Firmware, OP-TEE OS/Client/Test, kernel driver and u-boot etc.

# build
mkdir src

cd src

git clone -b morty git://git.yoctoproject.org/poky

git clone -b morty git://git.openembedded.org/meta-openembedded

git clone https://github.com/sukdo399/meta-raspberrypi.git

cd ..

source src/poky/oe-init-build-env rpi-build

echo 'MACHINE = "raspberrypi3-64"' >> conf/local.conf

echo 'ENABLE_UART = "1"' >> conf/local.conf

echo 'PREFERRED_VERSION_linux-raspberrypi = "4.6.%"' >> conf/local.conf



vi conf/bblayers.conf and add /home/sh.ko/work/oe/rpi3_yocto/src/meta-raspberrypi \ to BBLAYERS



bitbake rpi-optee-image


# test

flashing image to SD card

reboot rpi 3

mkdir -p /data/tee

chmod 755 -R /data

tee-supplicant &

xtest
