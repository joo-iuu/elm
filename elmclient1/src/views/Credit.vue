<template>
	<div class="wrapper">
		<header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>吃货豆</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
		</header>
		<!-- header部分 -->
		<li class="points">
			<p><img src="../assets/points.jpg"/>{{credit_copy.creditNum}}</p>
			
			<ul>吃货豆有效期7天，记得使用哟 <i class="fa fa-question-circle-o"></i></ul>
		</li>
		<h3>积分明细：</h3>
		
	
	        <div class="table">
	            <table class="listTab" cellspacing="0" cellpadding="0">
	                <thead>
	                                  <th>  序号   </th>
	                                  <th class='type'>  增加/减少  </th>
	                                  <th class='number'>  变动数目  </th>
	                                  <th class='createTime'>获得时间</th>
									  <th class='expiredTime'>过期时间</th>
	                              </thead>
	                <tbody>
	                	    <tr v-for="(item,index) in Arrcredit">
	                	      <td>{{index}}</td>
	                	      <td>
							         <span v-if ="item.channelType==1" class="red">减少</span>
							         <span v-else-if="item.channelType==0" class="green">增加</span>
							                                       
							        
					
							  </td>
							  
	                	      <td>{{item.num}}</td>
	                	      <td>{{item.createTime}}</td>
	                	       <td>{{item.expiredTime  == null ? '无' : item.expiredTime}}</td>
	                	    
	                	     
	                	    </tr>
	                </tbody>
	            </table> 
	       
	</div>
	
		
		
		
	<Footer></Footer>	
	</div>
</template>
<script>
	import Footer from '../components/Footer.vue'
  import Back from "@/components/Back.vue";
	export default{
		name:'Credit',
		data(){
			return{
				//userId:this.$route.query.userId,
				Arrcredit:[],
					user: {},
					userId: this.$route.query.userId,
					credit_copy:{},
			}
		},
		components: {
      Back,
			Footer: Footer
		},
		created(){
			 this.user = this.$getSessionStorage('user');
			      this.$axios.get('Credit/userId', {
			      params: {
			        userId: this.user.userId
			      }
			    }).
			then(response => {
			   			  this.Arrcredit = response.data;
			   			}).catch(error => {
			   			  console.error(error);
			   			});
					
			this.$axios.get('Credit/totalNum', {
			      params: {
			        userId: this.user.userId
			      }
			    }).
			then(response => {
			   			  this.credit_copy.creditNum = response.data;
			   			}).catch(error => {
			   			  console.error(error);
			   			});
			  },  
		methods:{
			isType(type){
				if(type == 0)
				return 增加;
				else if(type == 1)
				return 减少;
				else
				return;
			}
			
		}
		
	}
</script>
<style scoped>
	/*************** 总容器 ***************/
	.wrapper {
		width: 100%;
		height: 100%;
		background-color: #fff;
	}

	.wrapper header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.wrapper .points {
		width: 100%;
			height: 40vw;
		background-color: #fff;
	}
	
		.wrapper .points  p{
			box-sizing: border-box;
			padding-top: 12vw;
					
			display: flex;
			justify-content: center;
			align-items: center;
					
			user-select: none;
			cursor: pointer;
			font-size: 12vw;
			color: #666;
			}
		.wrapper .points  p img{
			display: block;
			width: 15vw;
			height: 15vw;
			border-radius: 20vw;
			}
		.wrapper .points ul{
		font-size: 2.8vw;
		color: #666;
		text-align: center;
		}
		
		.wrapper .list{ 
			width: 100%;
			margin-top: 12vw;
			color: #0097FF;
			font-size: 4.8vw;
			
		}
		.wrapper h3{
			
			margin-top: 3vw;
			margin-bottom: 2vw;
			box-sizing: border-box;
		   
			border-top: solid 1px #cecece;;
			background-color: #fff;
			font-size: 4vw;
			font-weight: 300;
			color: #999;
			
			
		}
		
		
		
		

		.wrapper .table {
			padding-bottom: 14vw;
		    width: 96%;
		      margin-left: 2%;
		      overflow-x:auto;
			  border-collapse: separate;
			    border-spacing: 1em 0.5em;
			    background-color: #e3e3e3;

		      }
			  
			  
			 .wrapper .table .listTab{
			      width: 100%;
			      font-size: 14px;
			      color: #666;
			      border: 1px solid #bcbcbd;
			      padding: 1px;
			      background-color: #f2f3ff;
				  border-collapse: separate;
				    border-spacing: 1em 0.5em;
				    background-color: #ddd;
			  }
			  
			 .wrapper .table.listTab thead > th{
			    background-color: #ecf0f4;
			    padding: 12px 14px;
			    text-align: center;
			    font-size: 9px;
			    border: 1px solid #ff5500;
				
			    min-width: 100px;
			    color:#292d31;
			    overflow: hidden;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			}
			.wrapper .table.listTab th.check{
			    min-width: 20px;
			}
			 .wrapper .table.listTab tbody > tr.check{
			    background-color: #dfeefc;
			}
			
			.wrapper .table.listTab tbody > tr td{
			    padding: 16px 8px;
			    text-align: center;
			    font-size: 9px;
			    border-bottom: 1px solid #ececed;
			    vertical-align: middle;
			    word-break: break-all;
			    min-width: 100px;
			    overflow: hidden;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			}
			
		   .wrapper .table.listTab tbody > tr td input{
		   border:1px solid #248bfc;
		   }
		   .wrapper .table.listTab tbody > tr td .green{
		   color: #72d34b;
		   font-weight:bold;
		   }
		   .wrapper .table.listTab tbody > tr td .red{
		   color: #f00;
		   font-weight:bold;
		   }
		   
	
	</style>