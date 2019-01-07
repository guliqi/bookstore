# Ether Store
这是关于 **Ether Store** 的简易文档，更加详情内容请阅读源码。由于技术不佳，文档和源码中一定会存在大量纰漏，敬请谅解。

## 项目介绍
**Ether Store** 项目是一个基于后端SSM框架开发的JavaWeb应用，项目的主要用途是提供一个线上C2C购书平台。平台里一共分为三种“用户”，分别是普通用户、书店商家、网站管理员，其中普通用户可以通过申请成为书店商家，申请需要网站管理员进行审核。

## 项目配置
**Ether Store** 项目在由Java语言 (jdk1.8) 编写，在 **Intellij IDEA 2018 Ultimate** 下运行，所有项目依赖由 **maven** 管理。

持久化数据库使用 **MySql 5.13**，内存数据库使用 **redis**，项目开发中借助以太坊测试链平台 **Ganache** 完成交易部分，这个平台的优势是所有区块链数据保存在内存中，不会持久化至硬盘，并且挖矿时间间隔不再固定，只有在广播区块数据时才会挖矿，这极大的方便了开发时的调试。

## 项目特色
### 结合区块链技术 ###
项目的支付部分借助以太坊的智能合约，完成了点对点的去信任交易。下面介绍智能合约的部分代码。

	enum State {Created, Paid , Delivered, Inactive}
	struct transaction {
		uint txId;
		address buyerAddr;
		address sellerAddr;
		uint price;
		State state;
	}
合约上每一笔交易都是一个 **transaction** 对象，其中 **txId** 是一个自增字段作为 **transaction** 的唯一标识，**buyerAddr** 和 **sellerAddr** 作为买家和卖家在以太坊上的账户地址，**price** 是这笔交易的价格，**state** 记录当前交易的状态，可以是已创建、已支付、已发货、已完成状态。

	event Created(address _sender, string _message, uint _txId);
	function create(address _sellerAddr, uint _price)
		public
	{
	    uint _txId = autoInc++;
		Transaction[_txId].txId = _txId; 
		Transaction[_txId].buyerAddr = msg.sender;
		Transaction[_txId].sellerAddr = _sellerAddr;
		Transaction[_txId].price = _price;
		Transaction[_txId].state = State.Created;
		transactions.push(_txId);
		emit Created(msg.sender, "created successfully", _txId);
	}
这是交易创建触发的智能合约函数，需要传入参数为卖家账户地址，商品价格，由于买家为合约的调用者，买家地址为 **msg.sender**，不用在函数参数中显式传入。当买家点击提交订单后，触发 **create** 函数，**create** 函数执行完毕后触发 **Created** 事件，传回数据给前端界面。其他的合约函数与此类同，请直接阅读源码。

## 项目地址 ##
```
https://github.com/guliqi/bookstore
```
