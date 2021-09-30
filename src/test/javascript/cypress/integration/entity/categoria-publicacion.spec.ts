import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('CategoriaPublicacion e2e test', () => {
  const categoriaPublicacionPageUrl = '/categoria-publicacion';
  const categoriaPublicacionPageUrlPattern = new RegExp('/categoria-publicacion(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/categoria-publicacions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/categoria-publicacions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/categoria-publicacions/*').as('deleteEntityRequest');
  });

  it('should load CategoriaPublicacions', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('categoria-publicacion');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CategoriaPublicacion').should('exist');
    cy.url().should('match', categoriaPublicacionPageUrlPattern);
  });

  it('should load details CategoriaPublicacion page', function () {
    cy.visit(categoriaPublicacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('categoriaPublicacion');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', categoriaPublicacionPageUrlPattern);
  });

  it('should load create CategoriaPublicacion page', () => {
    cy.visit(categoriaPublicacionPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('CategoriaPublicacion');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', categoriaPublicacionPageUrlPattern);
  });

  it('should load edit CategoriaPublicacion page', function () {
    cy.visit(categoriaPublicacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('CategoriaPublicacion');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', categoriaPublicacionPageUrlPattern);
  });

  it('should create an instance of CategoriaPublicacion', () => {
    cy.visit(categoriaPublicacionPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('CategoriaPublicacion');

    cy.get(`[data-cy="titulo"]`).type('withdrawal').should('have.value', 'withdrawal');

    cy.get(`[data-cy="descripcion"]`)
      .type('../fake-data/blob/hipster.txt')
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.get(`[data-cy="createAt"]`).type('2021-09-29T22:44').should('have.value', '2021-09-29T22:44');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', categoriaPublicacionPageUrlPattern);
  });

  it('should delete last instance of CategoriaPublicacion', function () {
    cy.visit(categoriaPublicacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('categoriaPublicacion').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categoriaPublicacionPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
