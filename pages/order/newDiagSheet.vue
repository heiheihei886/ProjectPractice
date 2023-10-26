//诊断结果提交页面
<template>
	<view>
		<!-- <view class="addr" @tap="selectAddress">
			<view class="icon">
				<image src="../../static/img/addricon.png" mode=""></image>
			</view>
			<view class="right">
				<view class="tel-name">
					<view class="name">
						{{recinfo.name}}
					</view>
					<view class="tel">
						{{recinfo.tel}}
					</view>
				</view>
				<view class="addres">
					{{recinfo.address.region.label}}
					{{recinfo.address.detailed}}
				</view>
			</view>
		</view> -->
		<!-- <view class="buy-list">
			<view class="row" v-for="(row,index) in buylist" :key="index">
				<view class="goods-info">
					<view class="img">
						<image :src="row.img"></image>
					</view>
					<view class="info">
						<view class="title">{{row.name}}</view>
						<view class="spec">日期：{{row.date}} </view>
						<view class="price-number">
							<view class="price">{{row.priority}}</view>
							<view class="number">
								
							</view>
						</view>
					</view>
				</view>
			</view>
		</view> -->
		<!-- 提示-备注 -->
		<view class="order">
			<view class="row">
				<view class="left">
					Disease name:
				</view>
				<view class="right">
					<input placeholder="Please enter the disease name" v-model="note_name" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Detailed symptom:
				</view>
				<view class="right">
					<input placeholder="Please enter the detailed symptom" v-model="note_content" />
				</view>
			</view>
			<view class="row">
				<view class="left">
					Department:
				</view>
				<view class="right">
					<!-- <input placeholder="请填写科室" v-model="note_department" /> -->
					<picker v-model="note_department" :range="departments" @change="onChange">
					    <view class="picker-content">{{ note_department || 'Please select the department' }}</view>
					</picker>
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
				<view class="btn" @tap="toPay">Submit Medical Record</view>
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
				buylist:[],		//列表
				goodsPrice:0.0,	//商品
				sumPrice:0.0,	//
				freight:12.00,	//运费
				// note_neirong:'',//诊断内容
				note_name:'',
				note_content:'',
				note_priority:'',
				note_date:'',
				note_department:'',
				int:1200,		
				deduction:0,	
				result:'2222',		//诊断结果
				recinfo:{}

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
			
			onChange1(event) {
			      this.note_priority = this.priorities[event.detail.value]
			},
			
			onChange2 :function(e){
				this.note_date = e.detail.value
			},
			
			//提交诊断结果
			toPay(){
				//商品列表
				// let paymentOrder = [];
				// let goodsid=[];
				
				// let len = this.buylist.length;
				// for(let i=0;i<len;i++){
				// paymentOrder.push(this.buylist[i]);
				// 	goodsid.push(this.buylist[i].id);
				// }
				// if(paymentOrder.length==0){
				// 	uni.showToast({title:'信息有误，请重新填写',icon:'none'});
				// 	return ;
				// }
				
				
				uni.request({
					url:'http://52.77.228.143:8080/insertSheet',
					
					data:{
						name : this.note_name,
						content: this.note_content,
						department: this.note_department,
						priority: this.note_priority,
						date: this.note_date,
						userid : uni.getStorageSync('userid')
					},
					success() {
						uni.showToast({
							title: "Submit successfully",
							duration: 1200
						});
						
					}
				});
				// uni.showLoading({
				// 	title:'正在提交就诊信息...',
				// 	duration: 1200
				// });
				setTimeout(function(){
					uni.navigateTo({
						url:'../tabBar/home/home'
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
// .order{
// 	width: 86%;
// 	padding: 10upx 3%;
// 	margin: 30upx auto 20upx auto;
// 	box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
// 	border-radius: 20upx;
// 	.row{
// 		margin: 20upx 0;
// 		height: 100upx;
// 		display: flex;
// 		.left{
// 			font-size: 28upx;
// 		}
// 		.right{
// 			margin-left: 40upx;
// 			font-size: 26upx;
// 			color: #999;
// 			input{
// 				font-size: 26upx;
// 				color: #999;
// 			}
// 		}
// 	}
// }
.order {
  background-color: #f7f7f7;
  padding: 20px;
  border-radius: 8px;
  margin: 20px;
  
  .row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    
    .left {
      width: 30%;
      font-weight: bold;
    }
    
    .right {
      width: 70%;
    }
  }
}

/* 修改下拉选项菜单的样式 */
picker {
  width: 100%;
  border: 1px solid #ccc;
  padding: 5px;
  border-radius: 5px;
  background-color: #fff;
  
  .picker-content {
    font-size: 16px;
    color: #000000;
  }
}

/* 输入框样式，这些样式将被下拉菜单取代 */
input {
  width: 100%;
  border: 1px solid #ccc;
  padding: 5px;
  border-radius: 5px;
  background-color: #fff;
  font-size: 16px;
  color: #333;
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
