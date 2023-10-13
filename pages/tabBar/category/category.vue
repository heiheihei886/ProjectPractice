<template>
	<view>
		<!-- 护士模块 -->
		<!-- 状态栏 -->
		<view class="status" :style="{position:headerPosition}"></view>
        <view class="header" :style="{position:headerPosition}">
			<view class="addr"><view class="icon location"></view>{{city}}</view>
			<view class="input-box">
				<input placeholder="默认关键字" placeholder-style="color:#c0c0c0;" @tap="toSearch()"/>
				<view class="icon search"></view>
			</view>
			<view class="icon-btn">
				<view class="icon tongzhi" @tap="toMsg"></view>
			</view>
		</view>
		<!-- 占位 -->
		<view class="place"></view>
		<view class="category-list">
			<!-- 左侧分类导航 -->
			<scroll-view  scroll-y="true" class="left" >
				
                <view class="row" v-for="(category,index) in categoryList" :key="category.id" :class="[index==showCategoryIndex?'on':'']" @tap="showCategory(index)">
					<view class="text">
						<view class="block"></view>
						{{category.title}}
					</view>
				</view>
				
            </scroll-view>
			<!-- 右侧子导航 -->
			<scroll-view  scroll-y="true" class="right" >
			    <view class="category" v-for="(category,index) in categoryList" :key="category.id" v-show="index==showCategoryIndex" >
					<!-- <view class="banner">
						<image :src="category.banner"></image>
					</view> -->
					
					<view class="list">
						<!-- 右侧自导航单体 -->
						<!-- <view class="box" v-for="(box,i) in category.list" :key="i" @tap="toCategory(box)">
							<image :src="'/static/img/category/list/'+box.img"></image>
							<view class="text">{{box.name}}</view>
						</view> -->
						
						<view class="row" v-for="(row,index1) in category.list" :key="row.id" >
							<!-- 删除按钮 -->
							<view class="menu" @tap.stop="deleteGoods(row.id)">
								<view class="icon shanchu"></view>
							</view>
							<!-- 商品 -->
							<view class="carrier" :class="[theIndex==index?'open':oldIndex==index?'close':'']" @touchstart="touchStart(index,$event)" @touchmove="touchMove(index,$event)" @touchend="touchEnd(index,$event)">
								<!-- checkbox -->
								<view class="checkbox-box" @tap="selected(index,index1)">
									<view class="checkbox">
										<view :class="[row.selected?'on':'']"></view>
									</view>
								</view>
								<!-- 商品信息 -->
								<view class="goods-info" @tap="toGoods(row)">
									<view class="img">
										<image :src="row.img"></image>
									</view>
									<view class="info">
										<view class="title">{{row.name}}</view>
										<view class="spec">{{row.priority}}</view>
										<view class="price-number">
											<view class="price">{{row.date}}</view>
											<!-- <view class="number">
												<view class="sub" @tap.stop="sub(index)">
													<view class="icon jian"></view>
												</view>
												<view class="input" @tap.stop="discard">
													<input type="number" v-model="row.number" @input="sum($event,index)" />
												</view>
												<view class="add"  @tap.stop="add(index)">
													<view class="icon jia"></view>
												</view>
											</view> -->
										</view>
									</view>
								</view>
							</view>
						</view>
						
						<!-- <view class="box" v-for"(box,i) in category.list" :key="i" @tap="toCategory(box)">
							<view class="box_head">
								<view class="priority">{{box.priority}}</view>
								<view class="date">{{box.date}}</view>
							</view> 
							<view class="box_body">
								<view class="symptom">{{box.symptom}}</view>
							</view>
						</view> -->
									
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>
<script>
	//高德SDK
	import amap from '@/common/SDK/amap-wx.js';
	export default {
		data() {
			return {
				showCategoryIndex:0,
				headerPosition:"fixed",
				city:"西安",
				userid:null,
				theIndex:null,
				oldIndex:null,
				isStop:false,
				// list1:[
				// 	{id:1,img:'/static/img/goods/p1.jpg',name:'护理1',priority:'高优先',price:'2022.3.15',number:1,selected:false},
				// 	{id:2,img:'/static/img/goods/p2.jpg',name:'护理2',priority:'高优先',price:'2022.3.12',number:1,selected:false},
				// 	{id:3,img:'/static/img/goods/p3.jpg',name:'护理3',priority:'中优先',price:'2022.3.14',number:1,selected:false},
				// 	{id:4,img:'/static/img/goods/p4.jpg',name:'护理4',priority:'低优先',price:'2022.2.23',number:1,selected:false},
				// 	{id:5,img:'/static/img/goods/p5.jpg',name:'护理5',priority:'低优先',price:'2022.3.17',number:1,selected:false}
					
				// ],
	
				//分类列表
				categoryList:[
					{id:1,title:'护理记录',banner:'/static/img/category/banner.jpg',list:[] ,
						selectedList:[]
					},
					{id:2,title:'配药记录',banner:'/static/img/category/banner.jpg',list:[
						{id:1,name:'配药1',	img:'1.jpg',date:'2022.1.12',selected:false},
						{id:2,name:'配药2',	img:'2.jpg',date:'2022.1.11',selected:false},
						{id:3,name:'配药3',	img:'3.jpg',date:'2022.1.13',selected:false},
						{name:'投影仪',	img:'4.jpg'},
						{name:'墨盒',	img:'5.jpg'},
						{name:'纸类',	img:'6.jpg'}
					]},
					{id:3,title:'出入记录',banner:'/static/img/category/banner.jpg',list:[
						{name:'茶具',	img:'1.jpg'},
						{name:'花瓶',	img:'2.jpg'},
						{name:'纸巾',	img:'3.jpg'},
						{name:'毛巾',	img:'4.jpg'},
						{name:'牙膏',	img:'5.jpg'},
						{name:'保鲜膜',	img:'6.jpg'},
						{name:'保鲜袋',	img:'7.jpg'}
					]},
					{id:4,title:'待定',banner:'/static/img/category/banner.jpg',list:[
						{name:'苹果',	img:'1.jpg'},
						{name:'芒果',	img:'2.jpg'},
						{name:'椰子',	img:'3.jpg'},
						{name:'橙子',	img:'4.jpg'},
						{name:'奇异果',	img:'5.jpg'},
						{name:'玉米',	img:'6.jpg'},
						{name:'百香果',	img:'7.jpg'}
					]},
				]
			}
		},
		onPageScroll(e){
			//兼容iOS端下拉时顶部漂移
			if(e.scrollTop>=0){
				this.headerPosition = "fixed";
			}else{
				this.headerPosition = "absolute";
			}
		},
		onLoad() {
			this.userid = uni.getStorageSync("userid");
			this.getNursingRecord();
			
			this.amapPlugin = new amap.AMapWX({  
				//高德地图KEY，随时失效，请务必替换为自己的KEY，参考：http://ask.dcloud.net.cn/article/35070
				key: '7c235a9ac4e25e482614c6b8eac6fd8e'  
			});
			//定位地址
			this.amapPlugin.getRegeo({  
				success: (data) => {
					this.city = data[0].regeocodeData.addressComponent.city.replace(/市/g,'');//把"市"去掉
				}  
			}); 
		},
		methods: {
			getNursingRecord(){
				uni.request({
					url:'http://localhost:8081/getNursingRecordByUserid',
					data:{
						userid:this.userid
					},
					success:(res)=>{
						//console.log(res.data)
						this.categoryList[0].list = res.data
					},
					fail:(res)=> {
						console.log(res.data);
					}
				})		
			},
			
			
			
			
			//消息列表
			toMsg(){
				uni.navigateTo({
					url:'../../msg/msg'
				})
			},
			//分类切换显示
			showCategory(index){
				this.showCategoryIndex = index;
			},
			//跳转到待诊断子界面
			toCategory(e){
				uni.setStorageSync('catName',e.name);
				uni.navigateTo({
					url: '../../goods/goods-list/goods-list?cid='+e.id+'&name='+e.name
				});
			},
			//搜索跳转
			toSearch(){
				uni.showToast({title: "建议跳转到新页面做搜索功能"});
			},
			//右侧list新增方法，诊单跳转
			toGoods(e){
				uni.showToast({title: '诊单'+e.id,icon:"none"});
				uni.navigateTo({
					url: '../../goods/goods' 
				});
			},
			//选中诊单，注意要修改
			async selected(index,index1){
				console.log('123',this.categoryList[index].list[index1])
				this.categoryList[index].list[index1].selected = this.categoryList[index].list[index1].selected?false:true;
				let i = this.categoryList[index].selectedList.indexOf(this.list[index].id);
				i>-1?this.selectedList.splice(i, 1):this.selectedList.push(this.list[index].id);
				this.isAllselected = this.selectedList.length == this.list.length;
				this.sum();
			}
		}
	}
</script>
<style lang="scss">

	.status {
		width: 100%;
		height: 0;
		position: fixed;
		z-index: 10;
		background-color: #fff;
		top: 0;
		/*  #ifdef  APP-PLUS  */
		height: var(--status-bar-height);//覆盖样式
		/*  #endif  */
		
	}

	.header{
		width: 92%;
		padding: 0 4%;
		height: 100upx;
		display: flex;
		align-items: center;
		position: fixed;
		top: 0;
		z-index: 10;
		background-color: #fff;
		/*  #ifdef  APP-PLUS  */
		top: var(--status-bar-height);
		/*  #endif  */
		
		.addr{
			width: 120upx;
			height: 60upx;
			flex-shrink: 0;
			display: flex;
			align-items: center;
			font-size: 28upx;
			.icon{
				height: 60upx; 
				margin-right: 5upx;
				display: flex;
				align-items: center;
				font-size: 42upx;
				color: #ffc50a;
			}
		}
		.input-box{
			width: 100%;
			height: 60upx;
			background-color: #f5f5f5;
			border-radius: 30upx;
			position: relative;
			display: flex;
			align-items: center;
			.icon{
				display: flex;
				align-items: center;
				position: absolute;
				top:0;
				right: 0;
				width: 60upx;
				height: 60upx;
				font-size: 34upx;
				color: #c0c0c0;
			}
			input{
				padding-left: 28upx;
				height: 28upx;
				font-size: 28upx;
			}
		}
		.icon-btn{
			width: 60upx;
			height: 60upx;
			flex-shrink: 0;
			display: flex;
			.icon{
				width: 60upx;
				height: 60upx;
				display: flex;
				justify-content: flex-end;
				align-items: center;
				font-size: 42upx;
			}
		}
	}
	.place{
		
		background-color: #ffffff;
		height: 100upx;
		/*  #ifdef  APP-PLUS  */
		margin-top: var(--status-bar-height);
		/*  #endif  */
	}
	.category-list{
		width: 100%;
		background-color: #fff;
		display: flex;
		.left,.right{
			position: absolute;
			
			top: 100upx;
			/*  #ifdef  APP-PLUS  */
			top: calc(100upx + var(--status-bar-height));
			/*  #endif  */
			bottom: 0upx;
		}
		.left{
			width: 24%;
			left: 0upx;
			background-color: #f2f2f2;
			.row{
				width: 100%;
				height: 90upx;
				display: flex;
				align-items: center;
				.text{
					width: 100%;
					position: relative;
					font-size: 28upx;
					display: flex;
					justify-content: center;
					color: #3c3c3c;
					.block{
						position: absolute;
						width: 0upx;
						left: 0;
					}
				}
				&.on{
					height: 100upx;
					background-color: #fff;
					.text{
						font-size: 30upx;
						font-weight: 600;
						color: #2d2d2d;
						.block{
							width: 10upx;
							height: 80%;
							top: 10%;
							background-color: #f06c7a;
						}
					}
				}
			}
		}
		.right{
		   width: 76%;
			left: 24%;
			.category{
				width: 94%;
				padding: 20upx 3%;
				.banner{
					width: 100%;
					height: 24.262vw;
					border-radius: 10upx;
					overflow: hidden;
					box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.3);
					image{
						width: 100%;
						height: 24.262vw;
					}
				}
				.list{
					margin-top: 40upx;
					width: 100%;
					display: flex;
					flex-wrap: wrap;
					// .box{
					// 	width: calc(71.44vw / 3);
					// 	margin-bottom: 30upx;
					// 	display: flex;
					// 	justify-content: center;
					// 	align-items: center;
					// 	flex-wrap: wrap;
					// 	image{
					// 		width: 60%;
					// 		height: calc(71.44vw / 3 * 0.6);
					// 	}
					// 	.text{
					// 		margin-top: 5upx;
					// 		width: 100%;
					// 		display: flex;
					// 		justify-content: center;
					// 		font-size: 26upx;
					// 	}
					// }
					
					.checkbox-box{
						display: flex;
						align-items: center;
						.checkbox{
							width: 25upx;
							height: 25upx;
							border-radius: 100%;
							border: solid 2upx #f06c7a;
							display: flex;
							justify-content: center;
							align-items: center;
							.on{
								width: 25upx;
								height: 25upx;
								border-radius: 100%;
								background-color: #f06c7a;
							}
						}
					}
					.row{
						width: calc(92%);
						height: calc(22vw + 40upx); 
						margin: 20upx auto;
						
						border-radius: 15upx;
						box-shadow: 0upx 5upx 20upx rgba(0,0,0,0.1);
						display: flex;
						align-items: center;
						position: relative;
						overflow: hidden;
						z-index: 4;
						border: 0;
						.menu{
							.icon{
								color: #fff;
								// font-size: 25upx;
							}
							position: absolute;
							width: 30%;
							height: 100%;
							right: 0;
							display: flex;
							justify-content: center;
							align-items: center;
							background-color: red;
							color: #fff;
							z-index: 2;
						}
						.carrier{
							@keyframes showMenu {
								0% {transform: translateX(0);}100% {transform: translateX(-30%);}
							}
							@keyframes closeMenu {
								0% {transform: translateX(-30%);}100% {transform: translateX(0);}
							}
							&.open{
								animation: showMenu 0.25s linear both;
							}
							&.close{
								animation: closeMenu 0.15s linear both;
							}
							background-color: #fff;
							.checkbox-box{
								padding-left: 10upx;
								flex-shrink: 0;
								height: 22vw;
								margin-right: 10upx;
							}
							position: absolute;
							width: 100%;
							padding: 0 0;
							height: 100%;
							z-index: 3;
							display: flex;
							align-items: center;
					
							.goods-info{
								width: 100%;
								display: flex;
								padding-right: 20upx;
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
										font-size: 20upx;
										background-color: #f3f3f3;
										color: #a7a7a7;
										height: 30upx;
										display: flex;
										align-items: center;
										padding: 0 10upx;
										border-radius: 15upx;
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
										height: 60upx;
										.price{
										}
										.number{
											display: flex;
											justify-content: center;
											align-items: flex-end;
											.input{
												width: 60upx;
												height: 60upx;
												margin: 0 10upx;
												background-color: #f3f3f3;
												input{
													width: 60upx;
													height: 60upx;
													display: flex;
													justify-content: center;
													align-items: center;
													text-align: center;
													font-size: 26upx;
												}
											}
											.sub ,.add{
												width: 45upx;
												height: 45upx;
												background-color: #f3f3f3;
												border-radius: 5upx;
												.icon{
													font-size: 22upx;
													width: 45upx;
													height: 45upx;
													display: flex;
													justify-content: center;
													align-items: center;
													
												}
											}
										}
									}
								}
							}
						}
					}
					
					// 下面是修改的box代码
					// .box{
					// 	width: 71.44vw ;//vw单位自适应布局单位
					// 	margin-bottom: 30upx;//下边距
					// 	display: flex;
					// 	justify-content: center;
					// 	align-items: center;
					// 	flex-wrap: wrap;
					// 	.box_head{
							
					// 		.priority{
								
					// 		}
					// 		.date{
								
					// 		}
					// 	}
					// 	.box_body{
							
					// 		.symptom{
								
					// 		}
					// 	}
					// }
				}
			}
		}
	}

</style>
