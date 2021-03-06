package se.helsingborg.oppna.solarie.index.facet.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.json.JSONException;
import org.json.JSONTokener;
import se.helsingborg.oppna.solarie.domain.*;
import se.helsingborg.oppna.solarie.index.SearchResult;
import se.helsingborg.oppna.solarie.index.facet.Facet;
import se.helsingborg.oppna.solarie.index.facet.FacetDefinition;
import se.helsingborg.oppna.solarie.index.facet.FacetValue;
import se.helsingborg.oppna.solarie.util.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kalle
 * @since 2014-10-03 20:12
 */
public class EnheterFacet extends FacetDefinition {

  private static String fieldName = "facet enheter";


  public EnheterFacet() {

  }

  @Override
  public void addFields(Document document, Indexable indexable) {
    GatherEnheter gatherEnheter = new GatherEnheter();
    indexable.accept(gatherEnheter);
    for (Enhet enhet : gatherEnheter.getEnheterna()) {
      document.add(new StringField(fieldName, valueFactory(enhet), Field.Store.NO));
    }
  }


  @Override
  public Facet facetFactory() {
    return new Facet("Enheter"){
      @Override
      protected List<FacetValue> valuesFactory(List<SearchResult> searchResults) {
        GatherEnheter gatherEnheter = new GatherEnheter();
        for (SearchResult searchResult : searchResults) {
          searchResult.getInstance().accept(gatherEnheter);
        }

        Set<String> facetValues = new HashSet<>();
        for (Enhet enhet : gatherEnheter.getEnheterna()) {
          facetValues.add(valueFactory(enhet));
        }

        List<FacetValue> values = new ArrayList<>(facetValues.size());
        for (final String facetValue : facetValues) {

          final MatchesVisitor matcher = new MatchesVisitor(facetValue);

          values.add(new FacetValue(searchResults, facetValue) {

            @Override
            public boolean matches(SearchResult searchResult) {
              return searchResult.getInstance().accept(matcher);
            }

            @Override
            public JSONObject toJSON() throws JSONException {
              JSONObject facetValueJSON = super.toJSON();
              facetValueJSON.put("query", new JSONObject(new JSONTokener("{ 'type': 'term', 'field': '"+fieldName+"', 'value': '" + facetValue + "' }")));
              return facetValueJSON;

            }

          });
        }
        return values;
      }

    };
  }



  private class GatherEnheter extends IndexableVisitor<Void> {
    private Set<Enhet> enheterna = new HashSet<>();

    @Override
    public Void visit(Arende ärende) {
      if (ärende.getEnhet() != null) {
        enheterna.add(ärende.getEnhet());
      }
      return null;
    }

    @Override
    public Void visit(Atgard åtgärd) {
      if (åtgärd.getEnhet() != null) {
        enheterna.add(åtgärd.getEnhet());
        // todo skall man även lägga till ärendets enhet?
        visit(åtgärd.getÄrende());
      }
      return null;
    }

    @Override
    public Void visit(Dokument dokument) {
      if (dokument.getÅtgärd() != null) {
        visit(dokument.getÅtgärd());
      }
      return null;
    }

    private Set<Enhet> getEnheterna() {
      return enheterna;
    }
  }

  private class MatchesVisitor extends IndexableVisitor<Boolean> {

    private String value;

    private MatchesVisitor(String value) {
      this.value = value;
    }

    @Override
    public Boolean visit(Arende ärende) {
      return value.equals(valueFactory(ärende.getEnhet()));
    }

    @Override
    public Boolean visit(Atgard åtgärd) {
      return value.equals(valueFactory(åtgärd.getEnhet()));
    }

    @Override
    public Boolean visit(Dokument dokument) {
      if (dokument.getÅtgärd() != null) {
        return value.equals(valueFactory(dokument.getÅtgärd().getEnhet()));
      }
      return false;
    }
  }

  private String valueFactory(Enhet enhet) {
    if (enhet == null) {
      return null;
    }
    return enhet.getNamn() == null ? enhet.getKod() : enhet.getNamn();
  }


}
