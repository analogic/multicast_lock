import 'dart:async';

import 'package:flutter/services.dart';

class MulticastLock {
  static const MethodChannel _channel =
      const MethodChannel('multicast_lock');

  Future acquire() => _channel.invokeMethod('acquire');
  Future release() => _channel.invokeMethod('release');
}
