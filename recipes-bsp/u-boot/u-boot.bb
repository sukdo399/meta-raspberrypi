#
# u-boot.bb:
#

DESCRIPTION = "u-boot"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
SECTION = "bootloader"

DEPENDS = "openssl"

PV="1+git${SRCPV}"

inherit uboot-config uboot-extlinux-config uboot-sign deploy

DEPENDS = ""
PARALLEL_MAKE=""

S = "${WORKDIR}/git"

BRANCH = "rpi3_initial_drop"
SRC_URI = "git://github.com/linaro-swg/u-boot;branch=${BRANCH} \
           file://0001-allow-setting-sysroot-for-libgcc-lookup.patch \
           file://0002-raspberry-pi-3-ARM-TF-support.patch \
	"

SRCREV = "10577db76e77a513180388df1f176a6642143c84"


export CROSS_COMPILE="${TARGET_PREFIX}"
export ARCH="arm64"
#export CFLAGS="--sysroot=${SYSROOT_DIRS}"

EXTRA_OEMAKE = "LIBGCC_LOCATE_CFLAGS=--sysroot=${STAGING_DIR_HOST} "

do_configure() {
	:
}

do_compile() {
	oe_runmake -C ${S} rpi_3_defconfig
	oe_runmake -C ${S} all
	oe_runmake -C ${S} tools
	${TARGET_PREFIX}as ${S}/arm-tf-rpi3/head.S -o ${S}/head.o
	${TARGET_PREFIX}objcopy -O binary ${S}/head.o ${S}/head.bin
	cat ${S}/head.bin ${S}/u-boot.bin > ${S}/u-boot-jtag.bin
	${S}/tools/mkenvimage -s 0x4000 -o ${S}/uboot.env ${S}/arm-tf-rpi3/uboot.env.txt
}

do_install() {
	:
}

do_deploy() {
	install -m 0644 ${S}/u-boot-jtag.bin ${DEPLOYDIR}/u-boot-jtag.bin
	install -m 0644 ${S}/uboot.env ${DEPLOYDIR}/uboot.env
}
addtask deploy before do_build after do_compile



