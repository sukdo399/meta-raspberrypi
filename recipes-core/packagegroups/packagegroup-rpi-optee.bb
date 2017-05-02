DESCRIPTION = "RaspberryPi Test Packagegroup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

RDEPENDS_${PN} = "\
    optee-os \
    optee-client \
    optee-test \
    u-boot \
"

RRECOMMENDS_${PN} = "\
    ${MACHINE_EXTRA_RRECOMMENDS} \
"
