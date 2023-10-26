//诊断结果提交页面
<template>
	<view>
		
		<view class="order">
			<view class="row">
				<view class="left">
					Username:
				</view>
				<view class="right">
					<input placeholder="Please enter the patient's username" v-model="note_username" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Nursing name:
				</view>
				<view class="right">
					<input placeholder="Please enter the nursing name" v-model="note_name" />
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
			<view class="row">
				<view class="left">
					Nursing content:
				</view>
				<view class="right">
					<input placeholder="Please enter the nursing content" v-model="note_content" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Priority:
				</view>
				<view class="right">
					<!-- <input placeholder="请填写优先级" v-model="note_priority" /> -->
					<picker v-model="note_priority" :range="priorities" @change="onChange1">
					    <view class="picker-content">{{ note_priority || 'Please select the priority' }}</view>
					</picker>
				</view>
			</view>
			<view class="row">
				<view class="left">
					Date:
				</view>
				<view class="right">
					<!-- <input placeholder="请填写日期" v-model="note_date" /> -->
					<picker  mode="date" :value="note_date" @change="onChange2">
					    <view class="picker-content">{{ note_date || 'Please select the date' }}</view>
					</picker>
				</view>
			</view>
			
			
		</view>
		
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
				priorities: ['High', 'Medium', 'Low'],
				buylist:[],		//订单列表
				goodsPrice:0.0,	//商品合计价格
				sumPrice:0.0,	//用户付款价格
				freight:12.00,	//运费
				// note_neirong:'',//诊断内容
				note_name:'',
				note_content:'',
				note_priority:'',
				note_date:'',
				note_department:'',
				note_username:'',
				userid:null,
				userid2:null,
				username:null,
				int:1200,		//抵扣积分
				deduction:0,	//抵扣价格
				result:'2222',		//诊断结果
				recinfo:{id:1,name:"大黑哥",head:"大",tel:"18816881688",address:{region:{"label":"广东省-深圳市-福田区","value":[18,2,1],"cityCode":"440304"},detailed:'深南大道1111号无名摩登大厦6楼A2'},isDefault:true}

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
			
			getUserid(){
				return new Promise((resolve, reject) => {	
					uni.request({
						url:'http://52.77.228.143:8080/getUserid',
						data:{
							username:this.note_username
						},
						success:(res) =>{
							this.userid2 = res.data
							resolve('suc');
						}
					})
				})	
			},
			
			onChange(event) {
			      this.note_department = this.departments[event.detail.value]
			},
			
			onChange1(event) {
			      this.note_priority = this.priorities[event.detail.value]
			},
			
			onChange2 :function(e){
				this.note_date = e.detail.value
			},
			//提交诊断结果
			async toPay(){
				await this.getUserid()
				uni.request({
					url:'http://52.77.228.143:8080/insertNursingRecord',				
					data:{
						name : this.note_name,
						content: this.note_content,
						priority: this.note_priority,
						date: this.note_date,
						userid : this.userid2,
						department:this.note_department
					},
					success: () => {
						uni.showToast({
							title:"Create successfully",
							duration:1200
						});
						setTimeout(function(){
							uni.navigateTo({
								url:'../../tabBar/home/home_nurse'
							})
						},1200)
					}
				});
				uni.showLoading({
					title:'Submitting information...',
					duration: 1200
				});
				setTimeout(function(){
					uni.switchTab({
						url: '../tabBar/home/home'
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
