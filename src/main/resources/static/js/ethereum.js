if (typeof web3 !== 'undefined') {
    web3 = new Web3(web3.currentProvider);
}
else {
    web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:7545"));
}
//web3.eth.defaultAccount = web3.eth.accounts[0];
var myContract = web3.eth.contract([
    {
        "constant": false,
        "inputs": [
            {
                "name": "_sellerAddr",
                "type": "address"
            },
            {
                "name": "_price",
                "type": "uint256"
            }
        ],
        "name": "create",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "name": "_txId",
                "type": "uint256"
            }
        ],
        "name": "deliver",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "name": "",
                "type": "uint256"
            }
        ],
        "name": "Transaction",
        "outputs": [
            {
                "name": "txId",
                "type": "uint256"
            },
            {
                "name": "buyerAddr",
                "type": "address"
            },
            {
                "name": "sellerAddr",
                "type": "address"
            },
            {
                "name": "price",
                "type": "uint256"
            },
            {
                "name": "state",
                "type": "uint8"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "name": "_txId",
                "type": "uint256"
            }
        ],
        "name": "pay",
        "outputs": [],
        "payable": true,
        "stateMutability": "payable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "name": "_txId",
                "type": "uint256"
            }
        ],
        "name": "receive",
        "outputs": [],
        "payable": true,
        "stateMutability": "payable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "name": "_txId",
                "type": "uint256"
            }
        ],
        "name": "abort",
        "outputs": [],
        "payable": true,
        "stateMutability": "payable",
        "type": "function"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": false,
                "name": "_sender",
                "type": "address"
            },
            {
                "indexed": false,
                "name": "_message",
                "type": "string"
            },
            {
                "indexed": false,
                "name": "_txId",
                "type": "uint256"
            }
        ],
        "name": "Created",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": false,
                "name": "_sender",
                "type": "address"
            },
            {
                "indexed": false,
                "name": "_message",
                "type": "string"
            }
        ],
        "name": "Paid",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": false,
                "name": "_sender",
                "type": "address"
            },
            {
                "indexed": false,
                "name": "_message",
                "type": "string"
            }
        ],
        "name": "Aborted",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": false,
                "name": "_sender",
                "type": "address"
            },
            {
                "indexed": false,
                "name": "_message",
                "type": "string"
            }
        ],
        "name": "Delivered",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": false,
                "name": "_sender",
                "type": "address"
            },
            {
                "indexed": false,
                "name": "_message",
                "type": "string"
            }
        ],
        "name": "Received",
        "type": "event"
    }
]);
var contract = myContract.at('0x9ce774fca17f26a04e2947ed5547909a8115cc7b');

// var createdEvent = contract.Created();
// var paidEvent = contract.Paid();
// var abortedEvent = contract.Aborted();
// var deliveredEvent = contract.Delivered();
// var receivedEvent = contract.Received();

// paidEvent.watch(function(error, result){
//     if (!error)
//     {
//         console.log(result);
//     } else {
//         console.log(error);
//     }
// });
//
// abortedEvent.watch(function(error, result){
//     if (!error)
//     {
//         console.log(result);
//     } else {
//         console.log(error);
//     }
// });
//
// deliveredEvent.watch(function(error, result){
//     if (!error)
//     {
//         console.log(result);
//     } else {
//         console.log(error);
//     }
// });
//
// receivedEvent.watch(function(error, result){
//     if (!error)
//     {
//         console.log(result);
//     } else {
//         console.log(error);
//     }
// });

function create(from, seller_address, price) {
    contract.create.sendTransaction(seller_address, price, {from: from, gas: 3000000}, function (error, result) {
        if (!error) console.log(result);
        else alert(error);
    });
}

function pay(from, value, tx_id) {
    contract.pay.sendTransaction(tx_id, {from: from, value: value}, function (error, result) {
        if (!error) console.log(result);
        else console.log(error);
    })
}

function abort(from, tx_id) {
    contract.abort.sendTransaction(tx_id, {from: from}, function (error, result) {
        if (!error) console.log(result);
        else console.log(error);
    })
}

function deliver(from, tx_id) {
    contract.deliver.sendTransaction(tx_id, {from: from}, function (error, result) {
        if (!error) console.log(result);
        else console.log(error);
    })
}

function receive(from, tx_id) {
    contract.receive.sendTransaction(tx_id, {from: from}, function (error, result) {
        if (!error) console.log(result);
        else console.log(error);
    })
}