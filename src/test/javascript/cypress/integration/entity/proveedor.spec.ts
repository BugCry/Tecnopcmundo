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

describe('Proveedor e2e test', () => {
  const proveedorPageUrl = '/proveedor';
  const proveedorPageUrlPattern = new RegExp('/proveedor(\\?.*)?$');
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
    cy.intercept('GET', '/api/proveedors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/proveedors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/proveedors/*').as('deleteEntityRequest');
  });

  it('should load Proveedors', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('proveedor');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Proveedor').should('exist');
    cy.url().should('match', proveedorPageUrlPattern);
  });

  it('should load details Proveedor page', function () {
    cy.visit(proveedorPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('proveedor');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', proveedorPageUrlPattern);
  });

  it('should load create Proveedor page', () => {
    cy.visit(proveedorPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Proveedor');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', proveedorPageUrlPattern);
  });

  it('should load edit Proveedor page', function () {
    cy.visit(proveedorPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('Proveedor');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', proveedorPageUrlPattern);
  });

  it('should create an instance of Proveedor', () => {
    cy.visit(proveedorPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Proveedor');

    cy.get(`[data-cy="nit"]`).type('Bélgica').should('have.value', 'Bélgica');

    cy.get(`[data-cy="nombre"]`).type('portals FTP bluetooth').should('have.value', 'portals FTP bluetooth');

    cy.get(`[data-cy="contacto"]`).type('payment Raton').should('have.value', 'payment Raton');

    cy.get(`[data-cy="direccion"]`).type('Desarrollador Méjico Iceland').should('have.value', 'Desarrollador Méjico Iceland');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', proveedorPageUrlPattern);
  });

  it('should delete last instance of Proveedor', function () {
    cy.visit(proveedorPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('proveedor').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proveedorPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
