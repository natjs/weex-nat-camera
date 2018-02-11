//
//  WeexNatCamera.m
//
//  Created by huangyake on 17/1/7.
//  Copyright © 2017 Instapp. All rights reserved.
//

#import "WeexNatCamera.h"
#import <WeexPluginLoader/WeexPluginLoader.h>
#import <NatCamera/NatCamera.h>

#define KOriginalPhotoImagePath   \
[[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingPathComponent:@"OriginalPhotoImages"]

@implementation WeexNatCamera
@synthesize weexInstance;

WX_PlUGIN_EXPORT_MODULE(nat/camera, WeexNatCamera)
WX_EXPORT_METHOD(@selector(captureImage::))
WX_EXPORT_METHOD(@selector(captureVideo::))

- (void)captureImage:(NSDictionary *)params :(WXModuleCallback)callback{
    [[NatCamera singletonManger] captureImage:params :^(id error,id result) {
        if (error) {
            if (callback) {
                callback(error);
            }
        } else {
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
        } else {
            if (callback) {
                callback(result);
            }
        }
    }];
}
@end
