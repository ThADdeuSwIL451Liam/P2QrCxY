// 代码生成时间: 2025-09-08 19:52:38
package com.example.demo.service;

import com.example.demo.exception.ShoppingCartException;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    /**<ol>
     * Adds an item to the shopping cart.
     *
     * @param cartItemId The ID of the cart item to add.
     * @param quantity The quantity of the item to add.
     * @return The added cart item.
     * @throws ShoppingCartException If an item with the given ID does not exist.
     */
    public CartItem addItemToCart(Long cartItemId, Integer quantity) {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with ID " + cartItemId + " not found.");
        }
        CartItem cartItem = item.get();
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartItemRepository.save(cartItem);
    }

    /**<ol>
     * Removes an item from the shopping cart.
     *
     * @param cartItemId The ID of the cart item to remove.
     * @throws ShoppingCartException If an item with the given ID does not exist.
     */
    public void removeItemFromCart(Long cartItemId) {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with ID " + cartItemId + " not found.");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    /**<ol>
     * Updates the quantity of an item in the shopping cart.
     *
     * @param cartItemId The ID of the cart item to update.
     * @param quantity The new quantity of the item.
     * @return The updated cart item.
     * @throws ShoppingCartException If an item with the given ID does not exist.
     */
    public CartItem updateItemQuantity(Long cartItemId, Integer quantity) {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with ID " + cartItemId + " not found.");
        }
        CartItem cartItem = item.get();
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero.");
        }
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    /**<ol>
     * Retrieves a list of all items in the shopping cart.
     *
     * @return A list of cart items.
     */
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }
}
