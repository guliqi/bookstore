pragma solidity^0.4.22;

contract Productsales {

	enum State {Created, Paid , Delivered, Inactive}

	struct transaction {
		uint txId;
		address buyerAddr;
		address sellerAddr;
		uint price;
		State state;
	}

	mapping (uint=>transaction) public Transaction;
	
	uint[] transactions;
	uint autoInc = 0;
	
	modifier condition(bool _condition) {
		require(_condition);
		_;
	}
	
	modifier forBuyer(uint _txId) {
		require(
			msg.sender == Transaction[_txId].buyerAddr,
			"买家账号地址错误!"
		);
		_;
	}
	
	modifier forSeller(uint _txId) {
		require(
			msg.sender == Transaction[_txId].sellerAddr,
			"卖家账号地址错误!"
		);
		_;
	}
	
	modifier inState(uint _txId, State _state) {
		require(
			Transaction[_txId].state == _state,
			"订单的状态不可操作."
		);
		_;
	}

	// create a transaction order by buyer
	event Created(address _sender, string _message, uint _txId);
	function create(address _sellerAddr, uint _price)
		public
	{
	    uint _txId = autoInc++; // cause dirty read?
		Transaction[_txId].txId = _txId; 
		Transaction[_txId].buyerAddr = msg.sender;
		Transaction[_txId].sellerAddr = _sellerAddr;
		Transaction[_txId].price = _price;
		Transaction[_txId].state = State.Created;
		transactions.push(_txId);
		emit Created(msg.sender, "created successfully", _txId);
	}

	// buyers pay for the goods according to transaction
	event Paid(address _sender, string _message);
	function pay(uint _txId) 
	    public 
	    payable
	    forBuyer(_txId)
	    inState(_txId, State.Created)
	{
		require(
		msg.value == Transaction[_txId].price,
		"请按商品价格付款！"
		);

		emit Paid(msg.sender, "paid successfully");
		Transaction[_txId].state = State.Paid;
	}

	// buyers abort the transaction before sellors send out goods
	event Aborted(address _sender, string _message);
	function abort(uint _txId)
		public
		payable
		forBuyer(_txId)
		inState(_txId, State.Paid)
	{
		emit Aborted(msg.sender, "aborted successfully");
		Transaction[_txId].state = State.Inactive;
		Transaction[_txId].buyerAddr.transfer(Transaction[_txId].price);
	}

	// sellor send out goods
	event Delivered(address _sender, string _message);
	function deliver(uint _txId)
		public
		forSeller(_txId)
		inState(_txId, State.Paid)
	{
		emit Delivered(msg.sender, "delivered successfully");
		Transaction[_txId].state = State.Delivered;
	}
	
	// buyer received goods
	event Received(address _sender, string _message);
	function receive(uint _txId)
		public
		forBuyer(_txId)
		inState(_txId, State.Delivered)
		payable
	{
		emit Received(msg.sender, "received successfully");
		Transaction[_txId].state = State.Inactive;
		Transaction[_txId].sellerAddr.transfer(address(this).balance);
	}
}