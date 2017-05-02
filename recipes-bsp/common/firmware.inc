RPIFW_SRCREV ?= "da504fcd4c673ecf85841ea80c28a8bb8b94e612"
RPIFW_SRC_URI ?= "https://github.com/raspberrypi/firmware/archive/${RPIFW_SRCREV}.tar.gz"
RPIFW_S ?= "${WORKDIR}/firmware-${RPIFW_SRCREV}"

SRC_URI = "${RPIFW_SRC_URI}"
SRC_URI[md5sum] = "f9853dc3c904216570fee171ad44a7ce"
SRC_URI[sha256sum] = "d1fb7c4524f2f9eac412d09cd4d1c7a9f77c5de197212ad4309c79c93f93a0d1"

PV = "${RPIFW_SRCREV}"

