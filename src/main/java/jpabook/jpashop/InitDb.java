package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member memberA = createMember("userA", "관악구", "서울시", "123");
            em.persist(memberA);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(memberA);
            Order order = Order.createOrder(memberA, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
        public void dbInit2(){
            Member memberB = createMember("userB", "강남구", "서울시", "123");
            em.persist(memberB);

            Book book1 = createBook("SPRING1 BOOK", 10000, 300);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 20000, 400);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(memberB);
            Order order = Order.createOrder(memberB, delivery, orderItem1, orderItem2);
            em.persist(order);

        }
    }

    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    private static Book createBook(String bookName, int price, int stockQuantity) {
        Book book1 = new Book();
        book1.setName(bookName);
        book1.setPrice(price);
        book1.setStockQuantity(stockQuantity);
        return book1;
    }

    private static Member createMember(String name, String street, String city, String zipcode) {
        Member memberA = new Member();
        memberA.setName(name);
        memberA.setAddress(new Address(city, street, zipcode));
        return memberA;
    }

}

