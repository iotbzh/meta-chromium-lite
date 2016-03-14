SUMMARY = "Chromium GPU library - Blink additions"
DESCRIPTION = "Blink web engine additions for the GPU library."
HOMEPAGE = "https://www.chromium.org"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

FILESEXTRAPATHS_prepend := ":${THISDIR}/../../shared:"

DEPENDS = "chromium-base chromium-skia chromium-ui-gfx chromium-ui-gl chromium-gpu chromium-cc chromium-blink"

NAME = "${@'${BPN}'.replace('chromium-', '')}"

SRCREV_${NAME} = "4feaed18c65b376675bebc0d100dc0a8df0c1c73"
SRC_URI = " \
           git://github.com/Tarnyko/chromium-${NAME}.git;name=${NAME} \
           file://LICENSE \
           file://CMakeLists.txt \
          "

S = "${WORKDIR}/git/gpu/blink"

inherit cmake pkgconfig

CXXFLAGS_append = " -I${STAGING_INCDIR}/chromium -I${STAGING_INCDIR}/chromium/skia/config -I${STAGING_INCDIR}/chromium/third_party/skia/include/core -I${STAGING_INCDIR}/chromium/third_party/skia/include/utils -I${STAGING_INCDIR}/chromium/third_party/skia/include/gpu"
LDFLAGS_append = " -L${STAGING_LIBDIR}/chromium -lbase -lskia -lui_gfx -lui_gl -lgpu -lcc -lblink"

do_configure_prepend() {
       cp ${WORKDIR}/LICENSE ${S}
       cp ${WORKDIR}/CMakeLists.txt ${S}
}

do_install_append() {
       cd ${S}
       mkdir -p ${D}${includedir}/chromium/gpu/blink
       cp --parents `find . -name "*.h"` ${D}${includedir}/chromium/gpu/blink
}

FILES_${PN} += "${libdir}/chromium/*.so"
FILES_${PN}-dbg += "${libdir}/chromium/.debug/*"
