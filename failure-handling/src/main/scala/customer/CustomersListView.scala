package customer

import org.scalactic.Good

import scala.util.{Success, Try}

class CustomersListView(customerService: CustomerService) {

  /**
   * Use [[customerService.customerName(id)]] for implementing a Seq of existing customer names for the given customer ids.
   * I.e. filter out all ids for that the Repo throws it's exceptions.
   *
   * You may freely use try or Try or another method for handling the exceptions of the underlying repo method.
   *
   * Hint: If you use Try() have a look at collect method in http://www.scala-lang.org/api/current/#scala.collection.Seq in conjunction with pattern matching.
   * Remember with pattern matching you may define a partial function!
   *
   * Do you see the burden that is put on the client when using Exceptions for "business return values"?
   */
  def listExistingCustomerNamesLangTryBased(ids: Seq[Int]): Seq[String] = {
    val possibleCustomerNames = ids.map { id => Try(customerService.customerName(id)) }
    possibleCustomerNames.collect {
      case Success(name) => name
    }
  }

  /**
   * Same as above but now use already "Try wrapped" [[customerService.customerNameTry(id)]].
   *
   * Hint: Have a look at collect method in http://www.scala-lang.org/api/current/#scala.collection.Seq in conjunction with pattern matching.
   * Remember with pattern matching you may define a partial function!
   */
  def listExistingCustomerNamesTryBased(ids: Seq[Int]): Seq[String] = {
    ids.map(customerService.customerNameTry).collect {
      case Success(name) => name
    }
  }

  /**
   * Same as above but now use [[customerService.customerNameOption(id)]].
   *
   * Hint: Have a look at map() and flatten() on Seq to solve this problem very concisely. Is there a way to be even more concise?
   */
  def listExistingCustomerNamesOptionBased(ids: Seq[Int]): Seq[String] = ids.flatMap(customerService.customerNameOption)

  /**
   * Same as above but now use [[customerService.customerNameOr(id)]].
   *
   * Hint: Have a look at collect method in http://www.scala-lang.org/api/current/#scala.collection.Seq in conjunction with pattern matching.
   * Remember with pattern matching you may define a partial function!
   */
  def listExistingCustomerNamesScalacticOrBased(ids: Seq[Int]): Seq[String] = {
    ids.map(customerService.customerNameOr).collect {
      case Good(name) => name
    }
  }
}
