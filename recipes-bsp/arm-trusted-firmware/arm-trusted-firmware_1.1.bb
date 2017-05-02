#
# arm-trusted-firmware.bb:
#
DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/license.md;md5=829bdeb34c1d9044f393d5a16c068371"

PV="1.1+git${SRCPV}"

inherit deploy

DEPENDS = "optee-os"
PARALLEL_MAKE=""

S = "${WORKDIR}/git"
#LIC_FILES_CHKSUM = 'file://license.md;md5=829bdeb34c1d9044f393d5a16c068371'

BRANCH = "rpi3_initial_drop"
SRC_URI = "git://github.com/linaro-swg/arm-trusted-firmware.git;branch=${BRANCH}"
#SRC_URI[md5sum] = "08802b153902fc468ba16889b85677bb"
#SRC_URI[sha256sum] = "0cebe7fb9b9b40a14d0fa403c9f635bff5df5c3fff39cc87c0ad970c19646d36"

#SRCREV ?= "${AUTOREV}"
SRCREV = "1da4e15529a32fa244f5e3effc9a90549beb1a26"

#COMPATIBLE_MACHINE = "pine64"
#PLATFORM_pine64 = "sun50iw1p1"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
#CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

PLATFORM = "rpi3"
OPTEE_OS_BIN = "tee.bin"

#ARM_TF_FLAGS = "BL32=${OPTEE_OS_BIN} DEBUG=1 V=0 CRASH_REPORTING=1 LOG_LEVEL=40 PLAT=${PLATFORM} SPD=opteed"
export CROSS_COMPILE="${TARGET_PREFIX}"
export CFLAGS="-O0 -gdwarf-2"

do_configure() {
	:
}

do_compile() {
	oe_runmake -C ${S} BL32="${OPTEE_OS_BIN}" DEBUG=1 V=0 CRASH_REPORTING=1 LOG_LEVEL=40 PLAT="${PLATFORM}" SPD=opteed all
}

do_install() {
	:
}

do_deploy() {
#	install -m 0644 ${S}/build/${PLATFORM}/release/bl31/bl31.elf ${DEPLOYDIR}/bl31-${MACHINE}.elf
#	install -m 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31-${MACHINE}.bin
#	install -m 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31.bin
	install -m 0644 ${S}/build/${PLATFORM}/debug/bl31.bin ${DEPLOYDIR}/bl31.bin
}
addtask deploy before do_build after do_compile
