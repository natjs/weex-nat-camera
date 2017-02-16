//
//  NatWeexCamera.m
//
//  Created by huangyake on 17/1/7.
//  Copyright © 2017 Nat. All rights reserved.
//

#import "NatWeexCamera.h"
#import "NatCamera.h"


#define KOriginalPhotoImagePath   \
[[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingPathComponent:@"OriginalPhotoImages"]
@implementation NatWeexCamera
WX_EXPORT_METHOD(@selector(captureImage::))
WX_EXPORT_METHOD(@selector(captureVideo::))

- (void)captureImage:(NSDictionary *)params :(WXModuleCallback)callback{
    
    [[NatCamera singletonManger] captureImage:params :^(id error,id result) {
        if (error) {
            if (callback) {
                callback(error);
            }
        }else{
            if (callback) {
                callback(result);
            }
        }
    }];
    
}

- (void)captureVideo:(NSDictionary *)params :(WXModuleCallback)callback{
    [[NatCamera singletonManger] captureVideo:params :^(id error,id result) {
        if (error) {
            if (callback) {
                callback(error);
            }
        }else{
            if (callback) {
                callback(result);
            }
        }
    }];
}
@end
