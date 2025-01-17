import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {UserInfoComponent} from "./component/user-info/user-info.component";
import {ProductComponent} from "./component/product/product.component";
import {LibraryComponent} from "./component/library/library.component";
import {AboutComponent} from "./component/about/about.component";
import {WishlistsComponent} from "./component/wishlists/wishlists.component";
import {WishlistProductsComponent} from "./component/wishlist-products/wishlist-products.component";
import {AuthGuard} from "./guard/auth.guard";
import {EditGameComponent} from "./component/edit-game/edit-game.component";
import {EditUserInfoAdminComponent} from "./component/edit-user-info-admin/edit-user-info-admin.component";
import {CheckoutComponent} from './component/checkout/checkout.component';
import {UserChatComponent} from './component/user-chat/user-chat.component';
import {AdminChatComponent} from './component/admin-chat/admin-chat.component';
import {FindUsers} from "./component/find-users/find-users";
import {LoginComponent} from "./component/login/login.component";
import {LogoutComponent} from "./component/logout/logout.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'product/:id', component: ProductComponent },
  { path: 'wishlists/:name', component: WishlistProductsComponent, canActivate: [AuthGuard]},
  { path: 'wishlists/other/:username/:wishlistName', component: WishlistProductsComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent, canActivate: [AuthGuard] },
  { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard] },
  { path: 'find-users', component: FindUsers, canActivate: [AuthGuard] },
  { path: 'user-info', component: UserInfoComponent, canActivate: [AuthGuard]},
  { path: 'library', component: LibraryComponent, canActivate: [AuthGuard]},
  { path: 'wishlists', component: WishlistsComponent, canActivate: [AuthGuard]},
  { path: 'editGames', component: EditGameComponent, canActivate: [AuthGuard]},
  { path: 'edit-user-data' , component: EditUserInfoAdminComponent, canActivate: [AuthGuard] },
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
  { path: 'about', component: AboutComponent },
  { path: 'userchat', component: UserChatComponent, canActivate: [AuthGuard] },
  { path: 'adminchat', component: AdminChatComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
