PACKAGECONFIG_GL:append:orange-pi-lite = " eglfs gles2 kms gbm"
PACKAGECONFIG_FONTS:orange-pi-lite = "fontconfig"
PACKAGECONFIG:append:orange-pi-lite = " libinput examples tslib xkbcommon gles2"
PACKAGECONFIG:remove:orange-pi-lite = "tests"

FILESEXTRAPATHS:prepend := "${THISDIR}/qtbase:"

SRC_URI:prepend:orange-pi-lite = " file://oe-device-extra.pri.h3"

do_configure:prepend:orange-pi-lite() {
    cp ${WORKDIR}/oe-device-extra.pri.h3 ${S}/mkspecs/oe-device-extra.pri
}

