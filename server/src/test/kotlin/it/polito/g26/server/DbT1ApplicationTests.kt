package it.polito.g26.server

import it.polito.g26.server.products.Product
import it.polito.g26.server.products.ProductRepository
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerRepository
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.profiles.expert.ExpertRepository
import it.polito.g26.server.profiles.manager.Manager
import it.polito.g26.server.profiles.manager.ManagerRepository
import it.polito.g26.server.ticketing.tickets.TicketRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DbT1ApplicationTests {
    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:latest")
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.jpa.hibernate.ddl-auto") {"create-drop"}
        }
    }
    @LocalServerPort
    protected var port: Int = 0
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var expertRepository: ExpertRepository
    @Autowired
    lateinit var managerRepository: ManagerRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var ticketRepository: TicketRepository

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1CustomerGet() {
        val customer = Customer("test@test.it", "Name","Surname", "city", "address")

        val savedCustomer = customerRepository.save(customer)

        val url = "http://localhost:$port/API/customer/${savedCustomer.email}"
        val response = restTemplate.getForEntity(url, Customer::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedCustomer, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2CustomerInsert() {
        val customer = Customer("test@test.it", "Name","Surname", "city", "address")

        val url = "http://localhost:$port/API/customer"
        val request = HttpEntity(customer)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val savedCustomer = customerRepository.findByEmail(customer.email)

        assertNotNull(savedCustomer)
        assertEquals(customer.name, savedCustomer?.name)
        assertEquals(customer.surname, savedCustomer?.surname)
        assertEquals(customer.email, savedCustomer?.email)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3CustomerUpdate() {
        val customer = Customer("test@test.it", "Name","Surname", "city", "address")
        customerRepository.save(customer)

        customer.name = "New name"
        customer.surname = "New surname"
        customer.city = "New city"
        customer.address = "New address"

        val url = "http://localhost:$port/API/customer/${customer.email}"
        val request = HttpEntity(customer)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, customer.email)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        val updatedCustomer = customerRepository.findByEmail(customer.email)

        assertNotNull(updatedCustomer)
        assertEquals(customer.name, updatedCustomer?.name)
        assertEquals(customer.surname, updatedCustomer?.surname)
        assertEquals(customer.city, updatedCustomer?.city)
        assertEquals(customer.address, updatedCustomer?.address)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ExpertGet() {
        val expert = Expert("Field1, Field2", "Name","Surname", "Email")

        val savedExpert = expertRepository.save(expert)

        val url = "http://localhost:$port/API/expert/${savedExpert.id}"
        val response = restTemplate.getForEntity(url, Expert::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedExpert, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ExpertGetByField() {
        val expert = Expert("Field1, Field2", "Name","Surname","Email")
        val field = "field1"

        val savedExpert = expertRepository.save(expert)

        val url = "http://localhost:$port/API/expert/field/${field}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<Expert>>() {}, field)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedExpert, response.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ExpertInsert() {
        val expert = Expert("Field1, Field2", "Name","Surname","Email")

        val url = "http://localhost:$port/API/expert"
        val request = HttpEntity(expert)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val savedExpert = expertRepository.save(expert)

        assertNotNull(savedExpert)
        assertEquals(expert.name, savedExpert.name)
        assertEquals(expert.surname, savedExpert.surname)
        assertEquals(expert.fields, savedExpert.fields)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test4ExpertUpdate() {
        val expert = Expert("Field1, Field2", "Name","Surname","Email")
        val savedExpert = expertRepository.save(expert)

        expert.fields = "Field3"
        expert.name = "New name"
        expert.surname = "New surname"

        val url = "http://localhost:$port/API/expert/${savedExpert.id}"
        val request = HttpEntity(expert)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, savedExpert.id)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        val updatedExpert = expertRepository.findById(savedExpert.id!!).get()

        assertNotNull(updatedExpert)
        assertEquals(expert.fields, updatedExpert.fields)
        assertEquals(expert.name, updatedExpert.name)
        assertEquals(expert.surname, updatedExpert.surname)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ManagerGet() {
        val manager = Manager("Name", "Surname","Email","Dep2")

        val savedManager = managerRepository.save(manager)

        val url = "http://localhost:$port/API/manager/${savedManager.id}"
        val response = restTemplate.getForEntity(url, Manager::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedManager, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ManagerInsert() {
        val manager = Manager("Name", "Surname","Email","Dep1")

        val url = "http://localhost:$port/API/manager"
        val request = HttpEntity(manager)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val savedManager = managerRepository.save(manager)

        assertNotNull(savedManager)
        assertEquals(manager.name, savedManager.name)
        assertEquals(manager.surname, savedManager.surname)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ManagerUpdate() {
        val manager = Manager("Name", "Surname", "Email", "Dep1")
        val savedManager = managerRepository.save(manager)

        manager.name = "New name"
        manager.surname = "New surname"

        val url = "http://localhost:$port/API/manager/${savedManager.id}"
        val request = HttpEntity(manager)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, savedManager.id)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        val updatedManager = managerRepository.findById(savedManager.id!!).get()

        assertNotNull(updatedManager)
        assertEquals(manager.name, updatedManager.name)
        assertEquals(manager.surname, updatedManager.surname)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ProductGetAll() {
        val Product = Product(name = "Name", category = "Category", brand = "Brand", price = 10.5)
        val Product2 = Product(name = "Name2", category = "Category2", brand = "Brand2", price = 10.5)

        val savedProduct = productRepository.save(Product)
        val savedProduct2 = productRepository.save(Product2)

        val url = "http://localhost:$port/API/Products"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<Product>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedProduct, response.body?.get(0))
        assertEquals(savedProduct2, response.body?.get(1))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ProductGet() {
        val Product = Product(name = "Name", category = "Category", brand = "Brand", price = 10.5)

        val savedProduct = productRepository.save(Product)

        val url = "http://localhost:$port/API/Product/${savedProduct.ean}"
        val response = restTemplate.getForEntity(url, Product::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedProduct, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ProductInsert() {
        val Product = Product(name = "Name", category = "Category", brand = "Brand", price = 10.5)

        val url = "http://localhost:$port/API/Product"
        val request = HttpEntity(Product)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val savedProduct = productRepository.save(Product)

        assertNotNull(savedProduct)
        assertEquals(Product.name, savedProduct.name)
        assertEquals(Product.category, savedProduct.category)
        assertEquals(Product.brand, savedProduct.brand)
        assertEquals(Product.price, savedProduct.price)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test4ProductUpdate() {
        val Product = Product(name = "Name", category = "Category", brand = "Brand", price = 10.5)
        val savedProduct = productRepository.save(Product)

        Product.name = "New name"
        Product.category = "New category"
        Product.brand = "New brand"
        Product.price = 12.3

        val url = "http://localhost:$port/API/Product/${savedProduct.ean}"
        val request = HttpEntity(Product)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, savedProduct.ean)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        val updatedProduct = productRepository.findById(savedProduct.ean!!).get()

        assertNotNull(updatedProduct)
        assertEquals(Product.name, updatedProduct.name)
        assertEquals(Product.category, updatedProduct.category)
        assertEquals(Product.brand, updatedProduct.brand)
        assertEquals(Product.price, updatedProduct.price)
    }

    /*
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1TicketGetAll() {
        val customer = Customer("test@test.it", "Name","Surname", "city", "address")
        val savedCustomer = customerRepository.save(customer)
        val expert = Expert("Field1, Field2", "Name","Surname")
        val savedExpert = expertRepository.save(expert)
        val Product = Product(name = "Name", category = "Category", brand = "Brand", price = 10.5)
        val savedProduct = ProductRepository.save(Product)

        val ticket = Ticket(
            customer = customer,
            expert = expert,
            product = Product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = Ticket(
            customer = customer,
            expert = expert,
            product = Product,
            issueType = "Issue2",
            description = "Description2",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val savedTicket = ticketRepository.save(ticket)
        val savedTicket2 = ticketRepository.save(ticket2)

        val url = "http://localhost:$port/API/tickets"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<Ticket>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(savedTicket, response.body?.get(0))
        assertEquals(savedTicket2, response.body?.get(1))
    }

     */
}