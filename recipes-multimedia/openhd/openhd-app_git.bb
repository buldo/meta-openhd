SUMMARY = "OpenHD"
DESCRIPTION = "OpenHD"
HOMEPAGE = "https://github.com/OpenHD/OpenHD"
SECTION = "console/utils"

# TODO: Change license
LICENSE="CLOSED"
inherit systemd cmake pkgconfig

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "openhd.service"

SRC_URI = "\
    gitsm://github.com/OpenHD/OpenHD.git;protocol=https;branch=dev-release \
    file://openhd.service \
"
SRCREV = "649f780abd11ae2dc41e83ea3516f61af55956dc"

S = "${WORKDIR}/git/OpenHD"

DEPENDS = "\
    boost \
    libsodium \
    libv4l \
    libpcap \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    " 

DEPENDS:rpi += " openhd-libcamera"

RDEPENDS:${PN} += "\
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    libpcap \
    libv4l \
    boost \
    libsodium \
    networkmanager \
"

RDEPENDS:${PN}:rpi += " openhd-libcamera"

EXTRA_OECMAKE = "\
    -DCMAKE_BUILD_TYPE=Debug \
    -DBoost_INCLUDE_DIR=${STAGING_INCDIR} \
    -DCMAKE_LIBRARY_PATH=${STAGING_LIBDIR} \
    -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
"

FILES:${PN} += "${systemd_unitdir}/system/openhd.service"

EXTRA_OECMAKE:append:raspberrypi0-2w = " -DENABLE_COMPILE_FLAGS_FOR_TARGET=armv8-neon"
EXTRA_OECMAKE:append:raspberrypi3 = " -DENABLE_COMPILE_FLAGS_FOR_TARGET=armv8-neon"
EXTRA_OECMAKE:append:raspberrypi4 = " -DENABLE_COMPILE_FLAGS_FOR_TARGET=armv8-neon"

# Build does not version solibs so we need to change the suffix
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_configure[network] =  "1"
do_compile[network] = "1"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/openhd.service ${D}${systemd_system_unitdir}
}
