//package com.project.shopapp.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "order_details")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class OrderDetail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    private Book book;
//
//    @Column(name = "price", nullable = false)
//    private Float price;
//
//    @Column(name = "number_of_products", nullable = false)
//    private int numberOfProducts;
//
//    @Column(name = "total_money", nullable = false)
//    private Float totalMoney;
//
//    @Column(name = "color")
//    private String color;
//}
