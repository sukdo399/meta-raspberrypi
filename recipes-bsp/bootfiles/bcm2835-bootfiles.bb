DESCRIPTION = "Closed source binary files to help boot the ARM on the BCM2835."
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://LICENCE.broadcom;md5=4a4d169737c0786fb9482bb6d30401d1"

inherit deploy

include recipes-bsp/common/firmware.inc

RDEPENDS_${PN} = "rpi-config"
DEPENDS = "u-boot arm-trusted-firmware"

COMPATIBLE_MACHINE = "raspberrypi"

S = "${RPIFW_S}/boot"

PR = "r3"

do_deploy() {
    install -d ${DEPLOYDIR}/${PN}

    for i in ${S}/*.elf ; do
        cp $i ${DEPLOYDIR}/${PN}
    done
    for i in ${S}/*.dat ; do
        cp $i ${DEPLOYDIR}/${PN}
    done
    for i in ${S}/*.bin ; do
        cp $i ${DEPLOYDIR}/${PN}
    done

    # Add stamp in deploy directory
    touch ${DEPLOYDIR}/${PN}/${PN}-${PV}.stamp

    # ARM Trusted Firmware
    dd if=/dev/zero of=scratch bs=1c count=131072 && \
    cat ${DEPLOY_DIR}/images/${MACHINE}/bl31.bin scratch > ${DEPLOYDIR}/bl31.tmp #&& \
    dd if=${DEPLOYDIR}/bl31.tmp of=${DEPLOYDIR}/bl31.head bs=1c count=131072 && \
    cat ${DEPLOYDIR}/bl31.head ${DEPLOY_DIR}/images/${MACHINE}/tee-pager.bin > ${DEPLOYDIR}/bcm2835-bootfiles/optee.bin

    cp ${DEPLOY_DIR}/images/${MACHINE}/u-boot-jtag.bin ${DEPLOY_DIR}/images/${MACHINE}/bcm2835-bootfiles/u-boot-jtag.bin
    cp ${DEPLOY_DIR}/images/${MACHINE}/uboot.env ${DEPLOY_DIR}/images/${MACHINE}/bcm2835-bootfiles/uboot.env
}

addtask deploy before do_package after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

