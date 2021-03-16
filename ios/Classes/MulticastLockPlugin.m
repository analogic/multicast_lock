#import "MulticastLockPlugin.h"
#if __has_include(<multicast_lock/multicast_lock-Swift.h>)
#import <multicast_lock/multicast_lock-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "multicast_lock-Swift.h"
#endif

@implementation MulticastLockPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMulticastLockPlugin registerWithRegistrar:registrar];
}
@end
