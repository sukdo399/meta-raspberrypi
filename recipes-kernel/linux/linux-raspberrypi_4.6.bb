FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

LINUX_VERSION ?= "4.6.3"

BRANCH = "electron752-rpi3-optee"
SRC_URI = "git://github.com/linaro-swg/linux.git;branch=${BRANCH} \
	   file://0001-enable-CONFIG_TEE-and-CONFIG_OPTEE.patch \
	"
SRCREV = "9f38d0f9d4aa32c8c2802be600d002eea222f9d1"

require linux-raspberrypi.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# A LOADADDR is needed when building a uImage format kernel. This value is not
# set by default in rpi-4.8.y and later branches so we need to provide it
# manually. This value unused if KERNEL_IMAGETYPE is not uImage.
KERNEL_EXTRA_ARGS += "LOADADDR=0x00008000"

do_configure_append() {
#    ${S}/scripts/kconfig/merge_config.sh ${S}/rpi3.conf
    cat ${S}/rpi3.conf >> .config
}
