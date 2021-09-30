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

describe('DetallesUsuario e2e test', () => {
  const detallesUsuarioPageUrl = '/detalles-usuario';
  const detallesUsuarioPageUrlPattern = new RegExp('/detalles-usuario(\\?.*)?$');
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
    cy.intercept('GET', '/api/detalles-usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/detalles-usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/api/detalles-usuarios/*').as('deleteEntityRequest');
  });

  it('should load DetallesUsuarios', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('detalles-usuario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DetallesUsuario').should('exist');
    cy.url().should('match', detallesUsuarioPageUrlPattern);
  });

  it('should load details DetallesUsuario page', function () {
    cy.visit(detallesUsuarioPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('detallesUsuario');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', detallesUsuarioPageUrlPattern);
  });

  it('should load create DetallesUsuario page', () => {
    cy.visit(detallesUsuarioPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('DetallesUsuario');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', detallesUsuarioPageUrlPattern);
  });

  it('should load edit DetallesUsuario page', function () {
    cy.visit(detallesUsuarioPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('DetallesUsuario');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', detallesUsuarioPageUrlPattern);
  });

  it('should create an instance of DetallesUsuario', () => {
    cy.visit(detallesUsuarioPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('DetallesUsuario');

    cy.get(`[data-cy="telefono"]`).type('invoice León analizada').should('have.value', 'invoice León analizada');

    cy.get(`[data-cy="identificacion"]`).type('Exclusivo gráfico Galicia').should('have.value', 'Exclusivo gráfico Galicia');

    cy.get(`[data-cy="ciudad"]`).type('transmitting Cine').should('have.value', 'transmitting Cine');

    cy.setFieldSelectToLastOfEntity('user');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', detallesUsuarioPageUrlPattern);
  });

  it('should delete last instance of DetallesUsuario', function () {
    cy.visit(detallesUsuarioPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('detallesUsuario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detallesUsuarioPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
