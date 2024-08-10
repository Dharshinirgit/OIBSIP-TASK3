# OIBSIP-TASK3
# ATM Interface 
*Login or register* 

The user should do either login or registration.

1. After this , the list of operations will be displayed to the user.
2. Initially the account balance of the user is zero.
3. In this case the user cannot perform operations like withdraw and amount transaction.
4. The user can use the deposit operation to deposit the amount into his/her account. But there  is one limitation, the user cannot deposit more than 10000 rs at a time, after deposit the balance in the account updated.
5.
* If the user wants to withdraw some amount then the user can use withdraw operation.
* Withdrwal of amount won't be allowed when the user has zero balance or balance less than 1000 rupees.
6.The user can check his account balance after operation using *check balance* option , The balance updated after each operation (withdraw,deposit, transferring amount ).
7.
*  When the user wants to transfer the amount to other account then the user can use *tranfer amount* option.
*   Here the user need to enter the recipient name and account number and amount to transfer.
* After entering all these details amount transferred to that recipient and balance updated.
8. If the user wants to see their past transaction history the user can use *past transaction history* option to get the details of their past transaction.
9. No user should have same pin number which was already in usage to ensure the uniqueness and to avoid conflicts the pin no validated when it is entered by the user.
10. While performing any operations in the ATM Interface suppose the user enters wrong pin then further operation won't proceed.
11. Once the user logged in , the user can perform operations like withdraw,deposit, balance check,amount transaction one after another simultaneously.
# Note
switch cases and looping concept used to perform operations.
# How to run
To compile:

javac filename.java
javac atminterface.java
To run:

java -cp .;connectorname.jar classname
