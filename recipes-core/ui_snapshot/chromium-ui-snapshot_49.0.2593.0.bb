SUMMARY = "Chromium UI Snapshot helper library"
DESCRIPTION = "Provides screenshooting abilities on top of UI Gfx and Aura."
HOMEPAGE = "https://www.chromium.org"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

FILESEXTRAPATHS_prepend := ":${THISDIR}/../../shared:"

DEPENDS = "chromium-base chromium-skia chromium-ui-gfx chromium-cc chromium-ui-compositor chromium-ui-aura"

NAME = "${@'${BPN}'.replace('chromium-', '')}"

SRCREV_${NAME} = "551a9e7dcddd58b4de318b05ab05a04bdabcfb99"
SRC_URI = " \
           git://github.com/Tarnyko/chromium-${NAME}.git;name=${NAME} \
           file://LICENSE \
           file://CMakeLists.txt \
          "

S = "${WORKDIR}/git/ui/snapshot"

inherit cmake pkgconfig

FULL_OPTIMIZATION = ""
CXXFLAGS_remove = "-fvisibility-inlines-hidden"
CXXFLAGS_append = " -I${STAGING_INCDIR}/chromium -I${STAGING_INCDIR}/chromium/skia/config -I${STAGING_INCDIR}/chromium/third_party/skia/include/core"
LDFLAGS_append = " -L${STAGING_LIBDIR}/chromium -lbase -lskia -lui_gfx -lcc -lui_compositor -lui_aura"

do_configure_prepend() {
       cp ${WORKDIR}/LICENSE ${S}
       cp ${WORKDIR}/CMakeLists.txt ${S}
}

do_install_append() {
       cd ${S}
       mkdir -p ${D}${includedir}/chromium/ui/snapshot
       cp --parents `find . -name "*.h"` ${D}${includedir}/chromium/ui/snapshot
}

FILES_${PN} += "${libdir}/chromium/*.so"
FILES_${PN}-dbg += "${libdir}/chromium/.debug/*"
