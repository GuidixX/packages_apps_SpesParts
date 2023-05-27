# SpesParts app
PRODUCT_PACKAGES += \
    SpesParts

# SpesParts sepolicy
BOARD_SEPOLICY_DIRS += packages/apps/SpesParts/sepolicy

# SpesParts init rc
PRODUCT_COPY_FILES += \
    packages/apps/SpesParts/init/init.spesparts.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/init.spesparts.rc
