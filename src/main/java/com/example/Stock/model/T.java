//package com.example.Stock.model;
//
//public class T {
//}


/*
CompleteFuture<User> userAsync =
CompleteFuture.supplyAsync(()->userService.getUser(userId))

CompleteFuture<UserPrefernces> UserPreferncesAsync =
CompleteFuture.supplyAsync(()->UserPrefernces.getUser(userId))
 CompleteFuture<Void> bothFutures
                            =CompleteFuture.allOf(userAsync,UserPreferncesAsync)
                            bothFutures.join();

                            will be like
Public Mono<User> getUserDetails(@PathVariable String userId){
    return userService.getUser(userId)
           .zipWith(userPreferencesService.getPreferences(userId))
           .map(t ->{
                User user = t.getT1();
                    user.setUserPreferences(t.getT2));
                    return user;
           });

}
** subscribe -> like forEach
* */