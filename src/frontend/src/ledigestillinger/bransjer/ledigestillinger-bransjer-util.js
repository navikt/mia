export const getNavnForYrkesomradeId = (yrkesomradeid, yrkesomrader) => {
  const omradeResultat = yrkesomrader.data.find(omrade => omrade.id === yrkesomradeid);
  return omradeResultat != null ? omradeResultat.navn : 'Alle';
};

export const getNavnForYrkesgruppeId = (yrkesgruppeid, yrkesgrupper) => {
  const gruppeResultat = yrkesgrupper.filter(gruppe => gruppe.id === yrkesgruppeid);
  return (gruppeResultat != null && gruppeResultat.length > 0) ? gruppeResultat.map(gruppe => gruppe.navn) : null;
};
