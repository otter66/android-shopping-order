package woowacourse.shopping.feature.order

import com.example.domain.cart.Cart
import woowacourse.shopping.data.order.OrderRepository
import woowacourse.shopping.model.mapper.toUi

class OrderPresenter(
    private val view: OrderContract.View,
    private val orderPendingCart: Cart,
    private val orderRepository: OrderRepository
) : OrderContract.Presenter {
    private var finalPrice = 0

    override fun loadOrderPendingCart() {
        view.setOrderPendingCart(orderPendingCart.toUi())
    }

    override fun calculatePrice() {
        orderRepository.requestFetchDiscountPolicy(
            success = { fixedDiscountPolicies ->
                val productsSum = orderPendingCart.getPickedProductsTotalPrice()
                val discountPrice = fixedDiscountPolicies.getDiscountPrice(productsSum)
                finalPrice = fixedDiscountPolicies.getFinalPrice(productsSum)

                view.setProductsSum(productsSum)
                view.setDiscountPrice(discountPrice)
                view.setFinalPrice(finalPrice)
            },
            failure = {}
        )
    }

    override fun navigateToOrderDetail() {
        orderRepository.requestAddOrder(
            cartIds = orderPendingCart.products.map { it.id },
            finalPrice = finalPrice,
            success = { orderId: Long ->
                view.showOrderDetailPage(orderId)
            },
            failure = {}
        )
    }
}
