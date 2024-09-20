import { createApp } from 'vue';
import App from '../App.vue';
//import router from './router';

import { createRouter, createWebHistory } from 'vue-router';
import Index from '../views/Index.vue';
import BusinessList from '../views/BusinessList.vue';
import BusinessInfo from '../views/BusinessInfo.vue';
import Login from '../views/Login.vue';
import Orders from '../views/Orders.vue';
import UserAddress from '../views/UserAddress.vue';
import Payment from '../views/Payment.vue';
import OrderList from '../views/OrderList.vue';
import AddUserAddress from '../views/AddUserAddress.vue';
import EditUserAddress from '../views/EditUserAddress.vue';
import Register from '../views/Register.vue';
import FirstPage from '../views/FirstPage.vue';
import MyInfo from '../views/MyInfo.vue';
import MyImg from '../views/MyImg.vue';
import ChangePwd from '../views/ChangePwd.vue';
import MyWallet from '../views/MyWallet.vue';
import BusinessList2 from '../views/BusinessList2.vue';
import Chatgpt from '../views/Chatgpt.vue'
import PaySuccess from '../views/PaySuccess.vue'
import Remark from '../views/Remark.vue'
import Credit from '../views/Credit.vue';
import BecomeMerchant from '../views/BecomeMerchant.vue';
import BusinessControl from '../views/BusinessControl.vue';
const routes=[
	{
		path: '/',
		name: 'Home',
		component: FirstPage
	},
	{
		path: '/firstPage',
		name: 'FirstPage',
		component: FirstPage
	},
	{
		path: '/index',
		name: 'Index',
		component: Index
	},
	{
		path: '/businessList',
		name: 'BusinessList',
		component: BusinessList
	},
	{
		path: '/paySuccess',
		name: 'PaySuccess',
		component: PaySuccess
	},
	{
		path: '/businessList2',
		name: 'BusinessList2',
		component: BusinessList2
	},
	{
		path: '/businessInfo',
		name: 'BusinessInfo',
		component: BusinessInfo
	},
	{
		path: '/businessControl',
		name: 'BusinessControl',
		component: BusinessControl
	},
	{
		path: '/chatgpt',
		name: 'Chatgpt',
		component: Chatgpt
	},
	{
		path: '/myInfo',
		name: 'MyInfo',
		component: MyInfo
	},
	{
		path: '/myImg',
		name: 'MyImg',
		component: MyImg
	},
	{
		path: '/changePwd',
		name: 'ChangePwd',
		component: ChangePwd
	},
	{
		path: '/credit',
		name: 'Credit',
		component: Credit
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/orders',
		name: 'Orders',
		component: Orders
	},
	{
		path: '/becomeMerchant',
		name: 'BecomeMerchant',
		component: BecomeMerchant
	},
	{
		path: '/userAddress',
		name: 'UserAddress',
		component: UserAddress
	},
	{
		path: '/payment',
		name: 'Payment',
		component: Payment
	},
	{
		path: '/orderList',
		name: 'OrderList',
		component: OrderList
	},
	{
		path: '/addUserAddress',
		name: 'AddUserAddress',
		component: AddUserAddress
	},
	{
		path: '/editUserAddress',
		name: 'EditUserAddress',
		component: EditUserAddress
	},
	{
		path: '/myWallet',
		name: 'MyWallet',
		component: MyWallet
	},

	{
		path: '/register',
		name: 'Register',
		component: Register
	},
	{
		path: '/remark',
		name: 'Remark',
		component: Remark
	}
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
});

createApp(App).use(router).mount('#app');

const originalPush = router.push;
router.push = function push(location) {
	return originalPush.call(this, location).catch(err => err);
};

export default router;

//解决重复路由报异常问题
/*const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})
export default router*/
