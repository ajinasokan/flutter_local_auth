# Local Auth plugin with support for older devices

Some older devices with older versions of Android doesn't advertise the feature flag `PackageManager.FEATURE_FINGERPRINT`. This makes the library think that the device doesn't have finger print sensor. This repo contains a patched version of local_auth lib with support for these devices.

## References

[https://github.com/flutter/flutter/issues/46227][https://github.com/flutter/flutter/issues/46227]
[https://issuetracker.google.com/issues/37132365](https://issuetracker.google.com/issues/37132365)
