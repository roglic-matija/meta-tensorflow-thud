DESCRIPTION = "Bazel build and test tool"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI[md5sum] = "5ee1a2bc666f2d0166c7915b647da5be"
SRC_URI[sha256sum] = "d350f80e70654932db252db380d2ec0144a00e86f8d9f2b4c799ffdb48e9cdd1"

SRC_URI = "https://github.com/bazelbuild/bazel/releases/download/4.0.0/bazel-4.0.0-dist.zip"

inherit native python3native

INHIBIT_SYSROOT_STRIP = "1"

CCACHE_DISABLE = "1"

DEPENDS = "coreutils-native \
           zip-native \
           openjdk-8-native \
          "

S="${WORKDIR}"

TS_DL_DIR ??= "${DL_DIR}"
do_compile () {
    export JAVA_HOME="${STAGING_LIBDIR_NATIVE}/jvm/openjdk-8-native"
    TMPDIR="${TOPDIR}/bazel" \
    VERBOSE=yes \
    EXTRA_BAZEL_ARGS="--distdir=${TS_DL_DIR} --python_path=python3" \
    ./compile.sh
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/output/bazel ${D}${bindir}
}

# Explicitly disable uninative
UNINATIVE_LOADER = ""
