//诊断结果提交页面
<template>
	<view>
		<!-- 提示-备注 -->
		<view class="order">
			<view class="row">
				<view class="left">
					Username:
				</view>
				<view class="right">
					<input placeholder="Please enter the new username" v-model="note_name" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Password:
				</view>
				<view class="right">
					<input placeholder="Please enter the password" v-model="note_password" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Department:
				</view>
				<view class="right">
					<picker v-model="note_department" :range="departments" @change="onChange">
					    <view class="picker-content">{{ note_department || 'Please select the department' }}</view>
					</picker>
				</view>
			</view>
			
		</view>
		<!-- 明细 -->
		<!-- <view class="detail">
			<view class="row">
				<view class="nominal">
					商品金额
				</view>
				<view class="content">
					￥{{goodsPrice|toFixed}}
				</view>
			</view>
			<view class="row">
				<view class="nominal">
					运费
				</view>
				<view class="content">
					￥+{{freight|toFixed}}
				</view>
			</view>
			<view class="row">
				<view class="nominal">
					积分抵扣
				</view>
				<view class="content">
					￥-{{deduction|toFixed}}
				</view>
			</view>
		</view> -->
		<view class="blck">
			
		</view>
		<view class="footer">
			<view class="settlement">
				<!-- <view class="sum">合计:<view class="money">￥{{sumPrice|toFixed}}</view></view> -->
				<view class="btn" @tap="toPay">Submit</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				departments: ['Internal medicine', 'Surgery', 'Pediatrics', 'Orthopedic', 'Dermatology'],
				buylist:[],		//订单列表
				goodsPrice:0.0,	
				sumPrice:0.0,	
				freight:12.00,	
				note_name:'',
				note_password:'',
				note_department:'',
				int:1200,		//抵扣积分
				deduction:0,	//抵扣价格
				

			};
		},
		
		onShow() {
			//页面显示时，加载订单信息
			uni.getStorage({
				key:'buylist',//id,img,name,date,priority,通过good文件toConformation传过来
				success: (ret) => {
					this.buylist = ret.data;
					this.goodsPrice=0;
					//合计
					let len = this.buylist.length;
					for(let i=0;i<len;i++){
						this.goodsPrice = this.goodsPrice + (this.buylist[i].number*this.buylist[i].price);
					}
					this.deduction = this.int/100;
					this.sumPrice = this.goodsPrice-this.deduction+this.freight;
				}
			});
			uni.getStorage({
				key:'selectAddress',
				success: (e) => {
					this.recinfo = e.data;
					uni.removeStorage({
						key:'selectAddress'
					})
				}
			})
		},
		onHide() {
			
		},
		onBackPress() {
			//页面后退时候，清除订单信息
			this.clearOrder();
		},
		filters: {
			toFixed:function(x) {
				return parseFloat(x).toFixed(2);
			}
		},
		methods: {
			clearOrder(){
				uni.removeStorage({
					key: 'buylist',
					success: (res)=>{
						this.buylist = [];
						console.log('remove buylist success');
					}
				});
			},
			
			onChange(event) {
			      this.note_department = this.departments[event.detail.value]
			},
			//提交诊断结果
			toPay(){
				if(this.note_name == ''){
					uni.showToast({
						title: 'Username cannot be empty',
						duration: 2000,
						icon:'none'
					});
					return false;
				}
				if(this.note_password == ''){
					uni.showToast({
						title: 'Password cannot be empty',
						duration: 2000,
						icon:'none'
					})
				}
				if(this.note_name.charAt(0) == '2'){
					uni.request({
						url:'http://52.77.228.143:8080/insertDoc',
						data:{
							name : this.note_name,
							password: this.note_password,
							department: this.note_department,
						},
						success(res) {
							if(res.data.code=="2"){
								uni.showToast({
									title: res.data.message,
									duration: 1200
								});
								setTimeout(function(){
									uni.navigateTo({
										url:'../tabBar/home/home_admin'
									})
								},1200)
							}else{
								uni.showToast({
									title: res.data.message,
									duration: 2000,
									icon:'none',
								})
							}
						}
					});
				}else{
					uni.showToast({
						title: 'To add a doctor account, please start with 2',
						duration: 2000,
						icon:'none',
					})
				}
				// uni.showLoading({
				// 	title:'正在提交就诊信息...',
				// 	duration: 1200
				// });
				setTimeout(function(){
					uni.navigateTo({
						url:'../tabBar/home/home_admin'
					})
				},1200);
				// setTimeout(()=>{
				// 	uni.setStorage({
				// 		key:'paymentOrder',
				// 		data:paymentOrder,
				// 		success: () => {
				// 			uni.hideLoading();
				// 			uni.redirectTo({
				// 				url:'../pay/success/success?amount='+this.amount
				// 			})
				// 		}
				// 	})
				// },1000)
				
			},
			//选择地址
			selectAddress(){
				uni.navigateTo({
					url:'../user/address/address?type=select'
				})
			}
		}
	}
</script>

<style lang="scss">
.addr{
	width: 86%;
	padding: 20upx 3%;
	margin: 30upx auto 20upx auto;
	box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
	border-radius: 20upx;
	display: flex;
	.icon{
		width: 80upx;
		height: 80upx;
		display: flex;
		align-items: center;
		image{
			width: 60upx;
			height: 60upx;
		}
	}
	.tel-name{
		width: 100%;
		display: flex;
		font-size: 32upx;
		.tel{
			margin-left: 40upx;
		}
	}
	.addres{
		width: 100%;
		font-size: 26upx;
		color: #999;
	}
}
.buy-list{
	width: 86%;
	padding: 10upx 3%;
	margin: 30upx auto 20upx auto;
	box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
	border-radius: 20upx;
	.row{
		margin: 30upx 0;
		.goods-info{
			width: 100%;
			display: flex;
			.img{
				width: 22vw;
				height: 22vw;
				border-radius: 10upx;
				overflow: hidden;
				flex-shrink: 0;
				margin-right: 10upx;
				image{
					width: 22vw;
					height: 22vw;
				}
			}
			.info{
				width: 100%;
				height: 22vw;
				overflow: hidden;
				display: flex;
				flex-wrap: wrap;
				position: relative;
				.title{
					width: 100%;
					font-size: 28upx;
					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 2;
					// text-align: justify;
					overflow: hidden;
				}
				.spec{
					font-size: 22upx;
					background-color: #f3f3f3;
					color: #a7a7a7;
					height: 40upx;
					display: flex;
					align-items: center;
					padding: 0 10upx;
					border-radius: 20upx;
					margin-bottom: 20vw;
				}
				.price-number{
					position: absolute;
					width: 100%;
					bottom: 0upx;
					display: flex;
					justify-content: space-between;
					align-items: flex-end;
					font-size: 28upx;
					height: 40upx;
					.price{
						color: #f06c7a;
					}
					.number{
						display: flex;
						justify-content: center;
						align-items: center;
						
					}
				}
			}
		}
	}
}
.order{
	width: 86%;
	padding: 10upx 3%;
	margin: 30upx auto 20upx auto;
	box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
	border-radius: 20upx;
	.row{
		margin: 20upx 0;
		height: 100upx;
		display: flex;
		.left{
			font-size: 28upx;
		}
		.right{
			margin-left: 40upx;
			font-size: 26upx;
			color: #999;
			input{
				width: 200%;
				font-size: 26upx;
				color: #999;
			}
		}
	}
}
.blck{
	width: 100%;
	height: 100upx;
}
.footer{
		width: 92%;
		padding: 0 4%;
		background-color: #fbfbfb;
		height: 100upx;
		display: flex;
		justify-content: flex-end;
		align-items: center;
		font-size: 28upx;
		position: fixed;
		bottom: 0upx;
		z-index: 5;
		
		.settlement{
			width: 80%;
			display: flex;
			justify-content: flex-end;
			align-items: center;
			.sum{
				width: 50%;
				font-size: 28upx;
				margin-right: 10upx;
				display: flex;
				justify-content: flex-end;
				.money{
					font-weight: 600;
				}
			}
			.btn{
				padding: 0 30upx;
				height: 60upx;
				background-color: #f06c7a;
				color: #fff;
				display: flex;
				justify-content: center;
				align-items: center;
				font-size: 30upx;
				border-radius: 40upx;
			}
		}
	}
.detail{
	width: 86%;
	padding: 10upx 3%;
	margin: 30upx auto 20upx auto;
	box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
	border-radius: 20upx;
	.row{
		height: 60upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		.nominal{
			font-size: 28upx;
		}
		.content{
			font-size: 26upx;
			color: #f06c7a;
		}
	}
}
</style>
