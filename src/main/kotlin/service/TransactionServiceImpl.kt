package service

import entity.Client

class TransactionServiceImpl : TransactionService {
    override fun withdraw(client: Client, amount: Int, comment: String) {
      //  TODO("Not yet implemented")
    }

    override fun deposit(client: Client, amount: Int, comment: String) {
       // TODO("Not yet implemented")
    }

    override fun getBalance(client: Client): Int {
       // TODO("Not yet implemented")
        return 0
    }
}