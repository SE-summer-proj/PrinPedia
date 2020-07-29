describe('e2e Test for PrinPedia', ()=>{
    // it('Does not do much!', ()=> {
    //     cy.visit("https://www.baidu.com")
    //     cy.get("#kw").type("cypress test")
    //     cy.wait(60)
    //     cy.get("#su").click()
    //     cy.contains('cypress website').click()
    // })
    
    beforeEach(() => {
        cy.visit("http://localhost:8080/#/index")
    })

    // it('search test', ()=> {
    //     // cy.visit("http://localhost:8080/#/index")
    //     cy.get("#search-input").type("四川省")
    //     cy.wait(60)
    //     cy.get("#search-button").click()
    //     cy.url().should('include','/result')
    //     cy.contains('四川省').click()
    //     cy.url().should('include','/entry')
    // })

    // it('relation test', ()=> {
    //     // cy.visit("http://localhost:8080/#/index")
    //     cy.get("#search-input").type("四川省")
    //     cy.wait(60)
    //     cy.get("#search-button").click()
    //     cy.url().should('include','/result')
    //     cy.contains('显示推荐').click()
    //     cy.contains('四川省')
    // })

    // it('login test', ()=> {
    //     // cy.visit("http://localhost:8080/#/index")
    //     cy.get("#login-button").click()
    //     cy.get("#login").click()
    //     cy.get("#login-input-username")
    //     .type('test1')
    //     cy.get("#login-input-password")
    //     .type('123456')
    //     cy.get("#login-button").click()
    // })

    // it('register test', ()=> {
    //     cy.get("#login-button").click()
    //     cy.get("#tab-register").click()
    //     cy.get("#register-input-username")
    //     .type('test2')
    //     cy.get("#register-input-password")
    //     .type('123456')
    //     cy.get("#register-input-password-confirm")
    //     .type('123456')
    //     cy.get("#register-input-email")
    //     .type('test2@email.com')
    //     cy.get("#register-button").click()
    // })

    it('login_cancel test', ()=> {
        cy.get("#login-button").click()
        cy.get("#cancel-button1").click() 
        cy.url().should('be',"http://localhost:8080/#/index")
    })

    it('register_cancel test', ()=> {
        cy.get("#login-button").click()
        cy.get("#tab-register").click()
        cy.get("#cancel-button2").click()
        cy.url().should('be',"http://localhost:8080/#/index")
    })

    it('edit_entry test', ()=> {

    })

    it('logout test', ()=> {

    })

    it('edit_user_info test', ()=> {

    })

    it('entry_jump test', ()=> {

    })





    
})