//
//  NatWeexCamera.h
//
//  Created by huangyake on 17/1/7.
//  Copyright © 2017 Nat. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <WeexSDK/WeexSDK.h>

@protocol NatCameraPro <WXModuleProtocol>

- (void)captureImage:(NSDictionary *)params :(WXModuleCallback)callback;
- (void)captureVideo:(NSDictionary *)params :(WXModuleCallback)callback;

@end

@interface NatWeexCamera : NSObject<NatCameraPro>
@end
